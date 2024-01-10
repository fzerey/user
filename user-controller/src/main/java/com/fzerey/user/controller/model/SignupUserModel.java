package com.fzerey.user.controller.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fzerey.user.service.user.dtos.SignupUserDto;
import com.fzerey.user.service.user.dtos.UserAttributeDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupUserModel {

    @NotBlank(message = "Username cannot be null")
    private String username;

    @NotBlank(message = "Password cannot be null")
    private String password;

    @NotBlank(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "Phone number cannot be null")
    private String phoneNumber;

    @NotBlank(message = "Group id cannot be null")
    private Long groupId;

    @Valid
    private List<UserAttributeModel> attributes;

    public SignupUserDto toDto() {
        var userAttributes = attributes == null || attributes.isEmpty() ? new ArrayList<UserAttributeDto>()
                : attributes.stream().map(UserAttributeModel::toDto).toList();
        return new SignupUserDto(username, password, email, phoneNumber, groupId, userAttributes);
    }
}
