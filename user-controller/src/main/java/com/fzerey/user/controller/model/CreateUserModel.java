package com.fzerey.user.controller.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fzerey.user.service.user.dtos.CreateUserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserModel {

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;

    @NotNull(message = "Group id cannot be null")
    private Long groupId;

    @Valid
    private List<UserAttributeModel> attributes;

    public CreateUserDto toUserDto() {
        return new CreateUserDto(username, password, email, phoneNumber, groupId,
                attributes.stream().map(UserAttributeModel::toDto).collect(Collectors.toList()));
    }
}
