
package io.github.krysiel86.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import io.github.krysiel86.model.VASTCompanion;
import io.github.krysiel86.model.VASTMediaFile;
import io.github.krysiel86.processor.VASTMediaPicker;

public class DefaultMediaPicker implements VASTMediaPicker {

    private static final String TAG = DefaultMediaPicker.class.getName();
    private static final int maxPixels = 5000;

    // These are the Android supported MIME types, see http://developer.android.com/guide/appendix/media-formats.html#core (as of API 18)
    String SUPPORTED_VIDEO_TYPE_REGEX = "video/.*(?i)(mp4|3gpp|mp2t|webm|matroska)";
    String SUPPORTED_IMAGE_TYPE_REGEX = "image/.*(?i)(jpeg|png|jpg)";

    private int deviceWidth;
    private int deviceHeight;
    private int deviceArea;
    private Context context;

    public DefaultMediaPicker(Context context) {

        this.context = context;
        setDeviceWidthHeight();
    }

    public DefaultMediaPicker(int width, int height) {

        setDeviceWidthHeight(width, height);
    }

    @Override
    // given a list of MediaFiles, select the most appropriate one.
    public VASTMediaFile pickVideo(List<VASTMediaFile> mediaFiles) {

        //make sure that the list of media files contains the correct attributes
        if (mediaFiles == null || prefilterMediaFiles(mediaFiles) == 0) {

            return null;
        }

        Collections.sort(mediaFiles, new AreaComparator());
        VASTMediaFile mediaFile = getBestMatch(mediaFiles);
        return mediaFile;
    }

    @Override
    public VASTCompanion pickImage(List<VASTCompanion> mediaFiles) {

        //make sure that the list of media files contains the correct attributes
        if (mediaFiles == null || prefilterImage(mediaFiles) == 0) {
//
            return null;
        }

//        Collections.sort(mediaFiles, new AreaComparator2());
        VASTCompanion mediaFile = getBestMatch2(mediaFiles);
        return mediaFile;

    }

    /*
     * This method filters the list of mediafiles and return the count.
     * Validate that the media file objects contain the required attributes for the Default Media Picker processing.
     *
     * 		Required attributes:
     * 			1. type
     * 			2. height
     * 			3. width
     * 			4. url
     */

    private int prefilterMediaFiles(List<VASTMediaFile> mediaFiles) {

        Iterator<VASTMediaFile> iter = mediaFiles.iterator();

        while (iter.hasNext()) {

            VASTMediaFile mediaFile = iter.next();
            // type attribute
            if (TextUtils.isEmpty(mediaFile.getType())) {
                VASTLog.d(TAG, "Validator error: mediaFile type empty");
                iter.remove();
                continue;
            }

            // mediaFile url
            if (TextUtils.isEmpty(mediaFile.getValue())) {

                VASTLog.d(TAG, "Validator error: mediaFile url empty");
                iter.remove();
            }

        }

        return mediaFiles.size();
    }

    private int prefilterImage(List<VASTCompanion> mediaFiles) {

        Iterator<VASTCompanion> iter = mediaFiles.iterator();

        while (iter.hasNext()) {

            VASTCompanion mediaFile = iter.next();
            // type attribute
            if (TextUtils.isEmpty(mediaFile.getCreativeType())) {
                VASTLog.d(TAG, "Validator error: mediaFile type empty");
                iter.remove();
                continue;
            }

            // mediaFile url
            if (TextUtils.isEmpty(mediaFile.getValue())) {

                VASTLog.d(TAG, "Validator error: mediaFile url empty");
                iter.remove();
            }


        }

        return mediaFiles.size();
    }

    private void setDeviceWidthHeight() {

        // get the device width and height of the device using the context
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;
        deviceArea = deviceWidth * deviceHeight;
    }

    private void setDeviceWidthHeight(int width, int height) {

        this.deviceWidth = width;
        this.deviceHeight = height;
        deviceArea = deviceWidth * deviceHeight;
    }

    private class AreaComparator implements Comparator<VASTMediaFile> {

        @Override
        public int compare(VASTMediaFile obj1, VASTMediaFile obj2) {

            // get area of the video of the two MediaFiles
            int obj1Area = obj1.getWidth().intValue() * obj1.getHeight().intValue();
            int obj2Area = obj2.getWidth().intValue() * obj2.getHeight().intValue();

            // get the difference between the area of the MediaFile and the area of the screen
            int obj1Diff = Math.abs(obj1Area - deviceArea);
            int obj2Diff = Math.abs(obj2Area - deviceArea);
//            VASTLog.v(TAG, "AreaComparator: obj1:" + obj1Diff + " obj2:" + obj2Diff);

            // choose the MediaFile which has the lower difference in area
            if (obj1Diff < obj2Diff) {

                return -1;

            } else if (obj1Diff > obj2Diff) {

                return 1;

            } else {

                return 0;
            }
        }
    }

    private boolean isMediaFileCompatible(VASTMediaFile media) {

        // check if the MediaFile is compatible with the device.
        // further checks can be added here
        return media.getType().matches(SUPPORTED_VIDEO_TYPE_REGEX);
    }

    private VASTMediaFile getBestMatch(List<VASTMediaFile> list) {

        VASTLog.d(TAG, "getBestMatch");

        // Iterate through the sorted list and return the first compatible media.
        // If none of the media file is compatible, return null
        Iterator<VASTMediaFile> iterator = list.iterator();

        while (iterator.hasNext()) {

            VASTMediaFile media = iterator.next();

            if (isMediaFileCompatible(media)) {

                return media;
            }
        }
        return null;
    }

    private VASTCompanion getBestMatch2(List<VASTCompanion> list) {

        VASTLog.d(TAG, "getBestMatch");

        // Iterate through the sorted list and return the first compatible media.
        // If none of the media file is compatible, return null
        Iterator<VASTCompanion> iterator = list.iterator();

        while (iterator.hasNext()) {

            VASTCompanion media = iterator.next();

            if (isMediaFileCompatible2(media)) {

                return media;
            }
        }
        return null;
    }


    private boolean isMediaFileCompatible2(VASTCompanion media) {

        // check if the MediaFile is compatible with the device.
        // further checks can be added here
        return media.getCreativeType().matches(SUPPORTED_IMAGE_TYPE_REGEX);
    }

    private class AreaComparator2 implements Comparator<VASTCompanion> {

        @Override
        public int compare(VASTCompanion obj1, VASTCompanion obj2) {

            // get area of the video of the two MediaFiles
            int obj1Area = obj1.getWidth().intValue() * obj1.getHeight().intValue();
            int obj2Area = obj2.getWidth().intValue() * obj2.getHeight().intValue();

            // get the difference between the area of the MediaFile and the area of the screen
            int obj1Diff = Math.abs(obj1Area - deviceArea);
            int obj2Diff = Math.abs(obj2Area - deviceArea);
            VASTLog.v(TAG, "AreaComparator: obj1:" + obj1Diff + " obj2:" + obj2Diff);

            // choose the MediaFile which has the lower difference in area
            if (obj1Diff < obj2Diff) {

                return -1;

            } else if (obj1Diff > obj2Diff) {

                return 1;

            } else {

                return 0;
            }
        }
    }
}
