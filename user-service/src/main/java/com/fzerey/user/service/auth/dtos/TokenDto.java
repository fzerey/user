package com.fzerey.user.service.auth.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDto {

    public TokenDto(String accessToken) {
        this.accessToken = accessToken;
    }

    private String accessToken;

}
