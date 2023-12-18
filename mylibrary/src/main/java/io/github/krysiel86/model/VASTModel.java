
package io.github.krysiel86.model;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import io.github.krysiel86.utils.VASTLog;
import io.github.krysiel86.utils.XmlTools;


public class VASTModel implements Serializable {

    private static String TAG = "tpmn";


    private transient Document vastsDocument;
    private String pickedMediaFileURL = null;
    private String pickedImage = null;

    private int pickedMediaFileWidth = 0;
    private int pickedMediaFileHeight = 0;

    // Tracking PATH
    private static final String PATH_INLINE_LINEAR_TRACKING = "/VASTS/VAST/Ad/InLine/Creatives/Creative/Linear/TrackingEvents/Tracking";
    private static final String PATH_INLINE_Companion_TRACKING = "/VASTS/VAST/Ad/InLine/Creatives/Creative/CompanionAds/TrackingEvents/Tracking";
    private static final String PATH_INLINE_NONLINEAR_TRACKING = "/VASTS/VAST/Ad/InLine/Creatives/Creative/NonLinearAds/TrackingEvents/Tracking";
    private static final String PATH_WRAPPER_LINEAR_TRACKING = "/VASTS/VAST/Ad/Wrapper/Creatives/Creative/Linear/TrackingEvents/Tracking";
    private static final String PATH_WRAPPER_NONLINEAR_TRACKING = "/VASTS/VAST/Ad/Wrapper/Creatives/Creative/NonLinearAds/TrackingEvents/Tracking";

    private static final String PATH_COMBINED_TRACKING = PATH_INLINE_LINEAR_TRACKING + "|" + PATH_INLINE_NONLINEAR_TRACKING + "|" + PATH_WRAPPER_LINEAR_TRACKING + "|" + PATH_WRAPPER_NONLINEAR_TRACKING
            + "|" + PATH_INLINE_Companion_TRACKING;

    // Direct items XPATH
    private static final String PATH_MEDIA_FILE = "//MediaFile";
    private static final String PATH_DURATION = "//Duration";
    private static final String PATH_VIDEO_CLICKS = "//VideoClicks";
    private static final String PATH_IMPRESSION = "//Impression";
    private static final String PATH_ERROR = "//Error";
    private static final String PATH_BANNER = "//Extension[@type='{EXTENSION_TYPE}']/Banner";

    private static final String EXTENSION_TYPE_KEY = "{EXTENSION_TYPE}";

    private static final String PATH_Companion = "//Companion";
    private static final String PATH_Static_Resource = "//StaticResource";
    private static final String PATH_Companion_Click_Through = "//CompanionClickThrough";


    public VASTModel(Document vasts) {

        this.vastsDocument = vasts;
    }

    public Document getVastsDocument() {

        return vastsDocument;
    }

    public HashMap<TRACKING_EVENTS_TYPE, List<String>> getTrackingUrls() {

        Log.d(TAG, "getTrackingUrls");

        List<String> tracking;

        HashMap<TRACKING_EVENTS_TYPE, List<String>> trackings = new HashMap<TRACKING_EVENTS_TYPE, List<String>>();

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            NodeList nodes = (NodeList) xpath.evaluate(PATH_COMBINED_TRACKING, vastsDocument, XPathConstants.NODESET);
            Node node;
            String trackingURL;
            String eventName;
            TRACKING_EVENTS_TYPE key;

            if (nodes != null) {

                for (int i = 0; i < nodes.getLength(); i++) {

                    node = nodes.item(i);
                    NamedNodeMap attributes = node.getAttributes();

                    eventName = (attributes.getNamedItem("event")).getNodeValue();

                    try {

                        key = TRACKING_EVENTS_TYPE.valueOf(eventName);

                    } catch (IllegalArgumentException e) {

                        Log.w(TAG, "Event:" + eventName + " is not valid. Skipping it.");
                        continue;
                    }

                    trackingURL = XmlTools.getElementValue(node);

                    if (trackings.containsKey(key)) {

                        tracking = trackings.get(key);
                        tracking.add(trackingURL);

                    } else {

                        tracking = new ArrayList<String>();
                        tracking.add(trackingURL);
                        trackings.put(key, tracking);
                    }
                }
            }

        } catch (Exception e) {

            Log.e(TAG, "getTrackingUrls e.getMessage() : " + e.getMessage());
            return null;
        }

        return trackings;
    }

    public List<VASTMediaFile> getMediaFiles() {

        Log.d(TAG, "getMediaFiles");

        ArrayList<VASTMediaFile> mediaFiles = new ArrayList<VASTMediaFile>();

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {

            NodeList nodes = (NodeList) xpath.evaluate(PATH_MEDIA_FILE, vastsDocument, XPathConstants.NODESET);
            Node node;
            VASTMediaFile mediaFile;
            String mediaURL;
            Node attributeNode;

            if (nodes != null) {

                for (int i = 0; i < nodes.getLength(); i++) {

                    mediaFile = new VASTMediaFile();
                    node = nodes.item(i);
                    NamedNodeMap attributes = node.getAttributes();

                    attributeNode = attributes.getNamedItem("apiFramework");
                    mediaFile.setApiFramework((attributeNode == null) ? null : attributeNode.getNodeValue());

                    attributeNode = attributes.getNamedItem("bitrate");
                    mediaFile.setBitrate((attributeNode == null) ? null : Integer.valueOf(attributeNode.getNodeValue()));

                    attributeNode = attributes.getNamedItem("delivery");
                    mediaFile.setDelivery((attributeNode == null) ? null : attributeNode.getNodeValue());

                    attributeNode = attributes.getNamedItem("id");
                    mediaFile.setId((attributeNode == null) ? null : attributeNode.getNodeValue());

                    attributeNode = attributes.getNamedItem("maintainAspectRatio");
                    mediaFile.setMaintainAspectRatio((attributeNode == null) ? null : Boolean.valueOf(attributeNode.getNodeValue()));

                    attributeNode = attributes.getNamedItem("scalable");
                    mediaFile.setScalable((attributeNode == null) ? null : Boolean.valueOf(attributeNode.getNodeValue()));

                    attributeNode = attributes.getNamedItem("type");
                    mediaFile.setType((attributeNode == null) ? null : attributeNode.getNodeValue());

                    attributeNode = attributes.getNamedItem("height");
                    mediaFile.setHeight((attributeNode == null) ? null : Integer.valueOf(attributeNode.getNodeValue()));


                    attributeNode = attributes.getNamedItem("width");
                    mediaFile.setWidth((attributeNode == null) ? null : Integer.valueOf(attributeNode.getNodeValue()));

                    mediaURL = XmlTools.getElementValue(node);

//                    Log.e("tpmn", "mediaURL : " + mediaURL);

                    mediaFile.setValue(mediaURL);

                    mediaFiles.add(mediaFile);
                }
            }

        } catch (Exception e) {

            Log.e(TAG, "getMediaFiles e.getMessage() : " + e.getMessage());
            return null;
        }

        return mediaFiles;
    }

    public List<VASTCompanion> getCompanion() {

        Log.d(TAG, "getCompanion");

        ArrayList<VASTCompanion> mediaFiles = new ArrayList<VASTCompanion>();

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {

            NodeList nodes = (NodeList) xpath.evaluate(PATH_Static_Resource, vastsDocument, XPathConstants.NODESET);
            Node node;
            VASTCompanion mediaFile;
            String mediaURL;
            Node attributeNode;

            if (nodes != null) {

                for (int i = 0; i < nodes.getLength(); i++) {

                    mediaFile = new VASTCompanion();
                    node = nodes.item(i);
                    NamedNodeMap attributes = node.getAttributes();


//
                    attributeNode = attributes.getNamedItem("creativeType");
                    mediaFile.setCreativeType((attributeNode == null) ? null : attributeNode.getNodeValue());

                    mediaURL = XmlTools.getElementValue(node);
                    Log.d(TAG, "getCompanion mediaURL : " + mediaURL);

                    mediaFile.setValue(mediaURL);

                    mediaFiles.add(mediaFile);
                }
            }

        } catch (Exception e) {

            Log.e(TAG, "getCompanion e.getMessage() : " + e.getMessage());
            return null;
        }

        return mediaFiles;
    }

    public String getDuration() {

        Log.d(TAG, "getDuration");

        String duration = null;

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {

            NodeList nodes = (NodeList) xpath.evaluate(PATH_DURATION, vastsDocument, XPathConstants.NODESET);
            Node node;

            if (nodes != null) {

                for (int i = 0; i < nodes.getLength(); i++) {

                    node = nodes.item(i);
                    duration = XmlTools.getElementValue(node);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "getDuration e.getMessage() : " + e.getMessage());
            return null;
        }

        return duration;
    }

    public String getExtensionURL(String type) {

        String result = null;

        String xPath = PATH_BANNER.replace(EXTENSION_TYPE_KEY, type);
        List<String> extensions = getListFromXPath(xPath);

        // We will use the first banner seen of this type
        if (extensions.size() > 0) {

            result = extensions.get(0);
        }

        return result;
    }

    public VideoClicks getVideoClicks() {

        VASTLog.d(TAG, "getVideoClicks");

        VideoClicks videoClicks = new VideoClicks();

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {

            NodeList nodes = (NodeList) xpath.evaluate(PATH_VIDEO_CLICKS, vastsDocument, XPathConstants.NODESET);
            Node node;

            if (nodes != null) {

                for (int i = 0; i < nodes.getLength(); i++) {

                    node = nodes.item(i);

                    NodeList childNodes = node.getChildNodes();

                    Node child;
                    String value = null;

                    for (int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {

                        child = childNodes.item(childIndex);
                        String nodeName = child.getNodeName();

                        if (nodeName.equalsIgnoreCase("ClickTracking")) {

                            value = XmlTools.getElementValue(child);
                            videoClicks.getClickTracking().add(value);

//                            Log.e("tpmn","ClickTracking : " + value);

                        } else if (nodeName.equalsIgnoreCase("ClickThrough")) {

                            value = XmlTools.getElementValue(child);
                            videoClicks.setClickThrough(value);
//                            Log.e("tpmn","ClickThrough : " + value);

                        } else if (nodeName.equalsIgnoreCase("CustomClick")) {

                            value = XmlTools.getElementValue(child);
                            videoClicks.getCustomClick().add(value);
                        }
                    }
                }
            }

        } catch (Exception e) {

            Log.e(TAG, "getVideoClicks e.getMessage() : " + e.getMessage());
            return null;
        }

        return videoClicks;
    }


    public List<String> getImpressions() {

        VASTLog.d(TAG, "getImpressions");

        List<String> list = getListFromXPath(PATH_IMPRESSION);

        return list;
    }

    public List<String> getErrorUrl() {

        VASTLog.d(TAG, "getErrorUrl");

        List<String> list = getListFromXPath(PATH_ERROR);

        return list;
    }

    private List<String> getListFromXPath(String xPath) {

        VASTLog.d(TAG, "getListFromXPath");

        ArrayList<String> list = new ArrayList<String>();

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {

            NodeList nodes = (NodeList) xpath.evaluate(xPath, vastsDocument, XPathConstants.NODESET);
            Node node;

            if (nodes != null) {

                for (int i = 0; i < nodes.getLength(); i++) {

                    node = nodes.item(i);
                    list.add(XmlTools.getElementValue(node));
                }
            }

        } catch (Exception e) {

            Log.e(TAG, "getListFromXPath e.getMessage() : " + e.getMessage());
            return null;
        }

        return list;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {

        VASTLog.d(TAG, "writeObject: about to write");
        oos.defaultWriteObject();

        String data = XmlTools.xmlDocumentToString(vastsDocument);
        // oos.writeChars();
        oos.writeObject(data);
        VASTLog.d(TAG, "done writing");
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {

        VASTLog.d(TAG, "readObject: about to read");
        ois.defaultReadObject();

        String vastString = (String) ois.readObject();
        VASTLog.d(TAG, "vastString data is:\n" + vastString + "\n");

        vastsDocument = XmlTools.stringToDocument(vastString);

        VASTLog.d(TAG, "done reading");
    }

    public String getPickedMediaFileURL() {

        return pickedMediaFileURL;
    }

    public void setPickedMediaFileURL(String pickedMediaFileURL) {

        this.pickedMediaFileURL = pickedMediaFileURL;
    }

    public String getPickedCompanionImage() {

        return pickedImage;
    }

    public void setPickedCompanionImage(String pickedImage) {

        this.pickedImage = pickedImage;
    }


    public int getPickedMediaFileWidth() {
        return pickedMediaFileWidth;
    }

    public void setPickedMediaFileWidth(int pickedMediaFileWidth) {
        this.pickedMediaFileWidth = pickedMediaFileWidth;
    }

    public int getPickedMediaFileHeight() {
        return pickedMediaFileHeight;
    }

    public void setPickedMediaFileHeight(int pickedMediaFileHeight) {
        this.pickedMediaFileHeight = pickedMediaFileHeight;
    }
}
