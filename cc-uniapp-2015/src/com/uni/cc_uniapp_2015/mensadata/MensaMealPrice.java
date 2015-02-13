package com.uni.cc_uniapp_2015.mensadata;

public class MensaMealPrice {

    String value;
    String type;

    public MensaMealPrice(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
