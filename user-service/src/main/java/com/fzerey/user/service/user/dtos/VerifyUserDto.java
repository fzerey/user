package com.fzerey.user.service.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerifyUserDto {
    private String username;
    private String code;
}
