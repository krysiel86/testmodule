
package io.github.krysiel86.processor;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilderFactory;

import io.github.krysiel86.model.VASTModel;
import io.github.krysiel86.model.VAST_DOC_ELEMENTS;
import io.github.krysiel86.utils.VASTLog;
import io.github.krysiel86.utils.VASTParser;
import io.github.krysiel86.utils.XmlTools;


/**
 * This class is responsible for taking a VAST 2.0 XML file, parsing it,
 * validating it, and creating a valid VASTModel object corresponding to it.
 * <p>
 * It can handle "regular" VAST XML files as well as VAST wrapper files.
 */
public final class VASTProcessor {

    private static final String TAG = VASTProcessor.class.getName();

    // Maximum number of VAST files that can be read (wrapper file(s) + actual
    // target file)
    private static final int MAX_VAST_LEVELS = 5;
    private static final boolean IS_VALIDATION_ON = false;

    private VASTMediaPicker mediaPicker;
    private VASTModel vastModel;
    private StringBuilder mergedVastDocs = new StringBuilder(500);

    public VASTProcessor(VASTMediaPicker mediaPicker) {

        this.mediaPicker = mediaPicker;
    }

    public VASTModel getModel() {

        return vastModel;
    }

    public int process(String xmlData) {

        VASTLog.d(TAG, "process");
        vastModel = null;
        InputStream is = null;

        try {
            is = new ByteArrayInputStream(xmlData.getBytes(Charset.defaultCharset().name()));
        } catch (UnsupportedEncodingException e) {
            VASTLog.e(TAG, e.getMessage(), e);
            return VASTParser.ERROR_XML_PARSE;
        }

        int error = processUri(is, 0);

        try {
            is.close();
        } catch (IOException e) {
        }

        if (error != VASTParser.ERROR_NONE) {

            return error;
        }

        Document mainDoc = wrapMergedVastDocWithVasts();
        vastModel = new VASTModel(mainDoc);

        if (mainDoc == null) {

            return VASTParser.ERROR_XML_PARSE;
        }

        if (!VASTModelPostValidator.validate(vastModel, mediaPicker)) {

            return VASTParser.ERROR_POST_VALIDATION;
        }

        if (!VASTModelPostImage.validate(vastModel, mediaPicker)) {
//
//            return VASTParser.ERROR_POST_VALIDATION2;
        }

        return VASTParser.ERROR_NONE;
    }

    private Document wrapMergedVastDocWithVasts() {

        mergedVastDocs.insert(0, "<VASTS>");
        mergedVastDocs.append("</VASTS>");

        String merged = mergedVastDocs.toString();
//        VASTLog.v(TAG, "Merged VAST doc:\n" + merged);

        Document doc = XmlTools.stringToDocument(merged);
        return doc;

    }

    private int processUri(InputStream is, int depth) {

        VASTLog.e("tpmn", TAG + " / processUri");

        if (depth >= MAX_VAST_LEVELS) {

            String message = "VAST wrapping exceeded max limit of " + MAX_VAST_LEVELS + ".";
            VASTLog.e(TAG, message);
            return VASTParser.ERROR_EXCEEDED_WRAPPER_LIMIT;
        }

        Document doc = createDoc(is);

        if (doc == null) {

            return VASTParser.ERROR_XML_PARSE;
        }

        merge(doc);

        // check to see if this is a VAST wrapper ad
        NodeList uriToNextDoc = doc.getElementsByTagName(VAST_DOC_ELEMENTS.vastAdTagURI.getValue());

        if (uriToNextDoc == null || uriToNextDoc.getLength() == 0) {
            // This isn't a wrapper ad, so we're done.
            return VASTParser.ERROR_NONE;

        } else {

            // This is a wrapper ad, so move on to the wrapped ad and process
            // it.
            VASTLog.e(TAG, "Doc is a wrapper. ");
            Node node = uriToNextDoc.item(0);
            String nextUri = XmlTools.getElementValue(node);
            VASTLog.e(TAG, "Wrapper URL: " + nextUri);
            InputStream nextInputStream = null;

            try {

                URL nextUrl = new URL(nextUri);
                nextInputStream = nextUrl.openStream();

            } catch (Exception e) {

                Log.e(TAG, "processUri e.getMessage() : " + e.getMessage());

                return VASTParser.ERROR_XML_OPEN_OR_READ;
            }

            int error = processUri(nextInputStream, depth + 1);
            try {

                nextInputStream.close();

            } catch (IOException e) {
            }
            return error;
        }
    }

    private Document createDoc(InputStream is) {

        VASTLog.d(TAG, "About to create doc from InputStream");
        try {

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();
            VASTLog.d(TAG, "Doc successfully created.");
            return doc;

        } catch (Exception e) {

            Log.e(TAG, "createDoc e.getMessage() : " + e.getMessage());
            return null;
        }
    }

    private void merge(Document newDoc) {

        VASTLog.d(TAG, "About to merge doc into main doc.");

        NodeList nl = newDoc.getElementsByTagName("VAST");

        Node newDocElement = nl.item(0);

        String doc = XmlTools.xmlDocumentToString(newDocElement);
        mergedVastDocs.append(doc);

        VASTLog.d(TAG, "Merge successful.");
    }
}
