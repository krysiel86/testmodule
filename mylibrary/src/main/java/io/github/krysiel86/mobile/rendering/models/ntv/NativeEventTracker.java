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

package io.github.krysiel86.mobile.rendering.models.ntv;

import androidx.annotation.Nullable;

import io.github.krysiel86.mobile.rendering.models.openrtb.bidRequests.Ext;

import java.util.ArrayList;

/**
 * Specifies what type of event tracking is supported
 */
public class NativeEventTracker {

    private final EventType eventType;
    private final ArrayList<EventTrackingMethod> eventTrackingMethods;
    private Ext ext;

    public NativeEventTracker(
            EventType eventType,
            ArrayList<EventTrackingMethod> eventTrackingMethods
    ) {
        this.eventType = eventType;
        this.eventTrackingMethods = eventTrackingMethods;
    }

    public EventType getEventType() {
        return eventType;
    }

    public ArrayList<EventTrackingMethod> getEventTrackingMethods() {
        return eventTrackingMethods;
    }

    public Ext getExt() {
        return ext;
    }

    public void setExt(Ext ext) {
        this.ext = ext;
    }

    public enum EventType {
        IMPRESSION(1),
        VIEWABLE_MRC50(2),
        VIEWABLE_MRC100(3),
        VIEWABLE_VIDEO50(4),
        CUSTOM(500),
        OMID(555);

        private int id;

        EventType(final int id) {
            this.id = id;
        }

        @Nullable
        public static EventType getType(Integer id) {
            if (id == null || id < 0) {
                return null;
            }

            EventType[] eventTypes = EventType.values();
            for (EventType eventType : eventTypes) {
                if (eventType.getId() == id) {
                    return eventType;
                }
            }

            EventType custom = EventType.CUSTOM;
            custom.setId(id);
            return custom;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            if (equals(CUSTOM) && !inExistingValue(id)) {
                this.id = id;
            }
        }

        private boolean inExistingValue(int id) {
            EventType[] possibleValues = getDeclaringClass().getEnumConstants();
            for (EventType value : possibleValues) {
                if (!value.equals(EventType.CUSTOM) && value.getId() == id) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum EventTrackingMethod {
        IMAGE(1),
        JS(2),
        CUSTOM(500);

        private int id;

        EventTrackingMethod(final int id) {
            this.id = id;
        }

        @Nullable
        public static EventTrackingMethod getType(Integer id) {
            if (id == null || id < 0) {
                return null;
            }

            EventTrackingMethod[] imageTypes = EventTrackingMethod.values();
            for (EventTrackingMethod imageType : imageTypes) {
                if (imageType.getId() == id) {
                    return imageType;
                }
            }

            EventTrackingMethod custom = EventTrackingMethod.CUSTOM;
            custom.setId(id);
            return custom;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            if (equals(CUSTOM) && !inExistingValue(id)) {
                this.id = id;
            }
        }

        private boolean inExistingValue(int id) {
            EventTrackingMethod[] possibleValues = getDeclaringClass().getEnumConstants();
            for (EventTrackingMethod value : possibleValues) {
                if (!value.equals(EventTrackingMethod.CUSTOM) && value.getId() == id) {
                    return true;
                }
            }
            return false;
        }
    }
}
