package com.fzerey.user.controller.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.fzerey.user.service.user.dtos.CreateUserDto;
import com.fzerey.user.service.user.dtos.UserAttributeDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserModel {

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

    public CreateUserDto toUserDto() {
        var userAttributes = attributes == null || attributes.isEmpty() ? new ArrayList<UserAttributeDto>()
                : attributes.stream().map(UserAttributeModel::toDto).collect(Collectors.toList());
        return new CreateUserDto(username, password, email, phoneNumber, groupId, userAttributes);
    }
}
