package com.fzerey.user.service.user.dtos;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserDto {

    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private Set<UserAttributeDto> attributes;

    public GetUserDto(Long id, String username, String email, String phoneNumber, Set<UserAttributeDto> attributes) {
        this.id = id;
        this.username = username;
        this.attributes = attributes;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}
