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

import io.github.krysiel86.mobile.rendering.video.vast.AdParameters;
import io.github.krysiel86.mobile.rendering.video.vast.Duration;
import io.github.krysiel86.mobile.rendering.video.vast.Icon;
import io.github.krysiel86.mobile.rendering.video.vast.Icons;
import io.github.krysiel86.mobile.rendering.video.vast.MediaFile;
import io.github.krysiel86.mobile.rendering.video.vast.MediaFiles;
import io.github.krysiel86.mobile.rendering.video.vast.Tracking;
import io.github.krysiel86.mobile.rendering.video.vast.TrackingEvents;
import io.github.krysiel86.mobile.rendering.video.vast.VASTParserBase;
import io.github.krysiel86.mobile.rendering.video.vast.VideoClicks;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class Linear extends VASTParserBase
{

	private final static String VAST_LINEAR = "Linear";
	private final static String VAST_ADPARAMETERS = "AdParameters";
	private final static String VAST_DURATION = "Duration";
	private final static String VAST_MEDIAFILES = "MediaFiles";
	private final static String VAST_TRACKINGEVENTS = "TrackingEvents";
	private final static String VAST_VIDEOCLICKS = "VideoClicks";
	private final static String VAST_ICONS = "Icons";

	private String skipOffset;

	private AdParameters adParameters;
	private Duration duration;
	private ArrayList<MediaFile> mediaFiles;
	private ArrayList<Tracking> trackingEvents;
	private VideoClicks videoClicks;
	private ArrayList<io.github.krysiel86.mobile.rendering.video.vast.Icon> icons;

	public Linear(XmlPullParser p) throws XmlPullParserException, IOException {

		p.require(XmlPullParser.START_TAG, null, VAST_LINEAR);

		skipOffset = p.getAttributeValue(null, "skipoffset");

		while (p.next() != XmlPullParser.END_TAG) {
			if (p.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = p.getName();
			if (name != null && name.equals(VAST_ADPARAMETERS))
			{
				p.require(XmlPullParser.START_TAG, null, VAST_ADPARAMETERS);
				adParameters = new AdParameters(p);
				p.require(XmlPullParser.END_TAG, null, VAST_ADPARAMETERS);
			}
			else if (name != null && name.equals(VAST_DURATION))
			{
				p.require(XmlPullParser.START_TAG, null, VAST_DURATION);
				duration = new Duration(p);
				p.require(XmlPullParser.END_TAG, null, VAST_DURATION);
			}
			else if (name != null && name.equals(VAST_MEDIAFILES))
			{
				p.require(XmlPullParser.START_TAG, null, VAST_MEDIAFILES);
				mediaFiles = (new MediaFiles(p)).getMediaFiles();
				p.require(XmlPullParser.END_TAG, null, VAST_MEDIAFILES);
			}
			else if (name != null && name.equals(VAST_TRACKINGEVENTS))
			{
				p.require(XmlPullParser.START_TAG, null, VAST_TRACKINGEVENTS);
				trackingEvents = (new TrackingEvents(p)).getTrackingEvents();
				p.require(XmlPullParser.END_TAG, null, VAST_TRACKINGEVENTS);
			}
			else if (name != null && name.equals(VAST_VIDEOCLICKS))
			{
				p.require(XmlPullParser.START_TAG, null, VAST_VIDEOCLICKS);
				videoClicks = new VideoClicks(p);
				p.require(XmlPullParser.END_TAG, null, VAST_VIDEOCLICKS);
			}
			else if (name != null && name.equals(VAST_ICONS))
			{
				p.require(XmlPullParser.START_TAG, null, VAST_ICONS);
				icons = (new Icons(p)).getIcons();
				p.require(XmlPullParser.END_TAG, null, VAST_ICONS);
			}
			else
			{
				skip(p);
			}
		}

	}

    public String getSkipOffset() {
		return skipOffset;
    }

    public AdParameters getAdParameters() {
		return adParameters;
    }

    public Duration getDuration() {
		return duration;
    }

    public ArrayList<MediaFile> getMediaFiles() {
		return mediaFiles;
    }

    public ArrayList<Tracking> getTrackingEvents() {
		return trackingEvents;
    }

    public VideoClicks getVideoClicks() {
		return videoClicks;
    }

    public ArrayList<Icon> getIcons() {
		return icons;
    }
}
