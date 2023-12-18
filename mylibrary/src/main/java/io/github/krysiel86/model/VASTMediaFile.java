
package io.github.krysiel86.model;

public class VASTMediaFile {

    private String value;
    private String id;
    private String delivery;
    private String type;
    private Integer bitrate;
    private Integer width;
    private Integer height;
    private Boolean scalable;
    private Boolean maintainAspectRatio;
    private String apiFramework;

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    public String getId() {

        return id;
    }

    public void setId(String value) {

        this.id = value;
    }

    public String getDelivery() {

        return delivery;
    }

    public void setDelivery(String value) {

        this.delivery = value;
    }

    public String getType() {

        return type;
    }

    public void setType(String value) {

        this.type = value;
    }

    public Integer getBitrate() {

        return bitrate;
    }

    public void setBitrate(Integer value) {

        this.bitrate = value;
    }

    public Integer getWidth() {

        return width;
    }

    public void setWidth(Integer value) {

        this.width = value;
    }

    public Integer getHeight() {

        return height;
    }

    public void setHeight(Integer value) {

        this.height = value;
    }

    public Boolean isScalable() {

        return scalable;
    }

    public void setScalable(Boolean value) {

        this.scalable = value;
    }

    public Boolean isMaintainAspectRatio() {

        return maintainAspectRatio;
    }

    public void setMaintainAspectRatio(Boolean value) {

        this.maintainAspectRatio = value;
    }

    public String getApiFramework() {

        return apiFramework;
    }

    public void setApiFramework(String value) {

        this.apiFramework = value;
    }

    @Override
    public String toString() {

        return "MediaFile [value=" + value + ", id=" + id + ", delivery=" + delivery + ", type=" + type + ", bitrate=" + bitrate + ", width=" + width + ", height=" + height + ", scalable=" + scalable + ", maintainAspectRatio=" + maintainAspectRatio + ", apiFramework=" + apiFramework + "]";
    }
}
