package com.fzerey.user.service.user.dtos;

public class UserAttributeDto {

    public UserAttributeDto(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private String key;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
