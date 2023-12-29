package com.fzerey.user.service.user.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateUserDto {
    public UpdateUserDto(Long id, String username, List<UserAttributeDto> attributes) {
        this.id = id;
        this.username = username;
        this.attributes = attributes;
    }

    private Long id;

    private String username;

    private List<UserAttributeDto> attributes = new ArrayList<>();

}