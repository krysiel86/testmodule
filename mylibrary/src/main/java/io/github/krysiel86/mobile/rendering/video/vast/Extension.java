/*
 *    Copyright 2018-2021 Prebid.org, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.krysiel86.mobile.rendering.video.vast;

import io.github.krysiel86.mobile.rendering.video.vast.AdVerifications;
import io.github.krysiel86.mobile.rendering.video.vast.VASTParserBase;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class Extension extends VASTParserBase {

	private String type;
	private AdVerifications adVerifications;

	private final static String VAST_AD_VERIFICATIONS = "AdVerifications";
	private final static String VAST_EXTENSION = "Extension";

	public Extension(XmlPullParser p) throws XmlPullParserException, IOException {
		type = p.getAttributeValue(null, "type");

		p.require(XmlPullParser.START_TAG, null, VAST_EXTENSION);
		while (p.next() != XmlPullParser.END_TAG) {

			if (p.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = p.getName();
			if (name != null && name.equals(VAST_AD_VERIFICATIONS)) {
				p.require(XmlPullParser.START_TAG, null, VAST_AD_VERIFICATIONS);
				adVerifications = new AdVerifications(p);
				p.require(XmlPullParser.END_TAG, null, VAST_AD_VERIFICATIONS);
			} else {
				skip(p);
			}
		}
	}

    public String getType() {
		return type;
    }

    public AdVerifications getAdVerifications() {
		return adVerifications;
    }
}