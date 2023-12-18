
package io.github.krysiel86.model;

import java.math.BigInteger;

public class VASTCompanion {

    private String creativeType;
    private String value;
    private BigInteger width;
    private BigInteger height;

    public BigInteger getWidth() {

        return width;
    }

    public void setWidth(BigInteger value) {

        this.width = value;
    }

    public BigInteger getHeight() {

        return height;
    }

    public void setHeight(BigInteger value) {

        this.height = value;
    }

    public String getValue() {

        return value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    public String getCreativeType() {
        return creativeType;
    }

    public void setCreativeType(String creativeType) {
        this.creativeType = creativeType;
    }


    @Override
    public String toString() {

        return "MediaFile [ value=" + value + "creativeType=" + creativeType + "]";
    }
}
