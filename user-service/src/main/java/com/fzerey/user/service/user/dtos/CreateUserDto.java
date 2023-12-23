package com.fzerey.user.service.user.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {

    public CreateUserDto(String username, String password, String email, String phoneNumber, Long groupId,
            List<UserAttributeDto> attributes) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.groupId = groupId;
        this.attributes = attributes;
    }

    private String username;

    private String password;

    private String email;
    private String phoneNumber;
    private Long groupId;

    private List<UserAttributeDto> attributes;

}