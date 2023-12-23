package com.fzerey.user.service.auth.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {

    public LoginUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;
}
