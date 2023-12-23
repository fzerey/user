package com.fzerey.user.controller.model;

import javax.validation.constraints.NotNull;

import com.fzerey.user.service.user.dtos.UserAttributeDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAttributeModel {

    @NotNull(message = "Key cannot be null")
    private String key;

    @NotNull(message = "Value cannot be null")
    private String value;

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public UserAttributeDto toDto() {
        return new UserAttributeDto(key, value);
    }

}
