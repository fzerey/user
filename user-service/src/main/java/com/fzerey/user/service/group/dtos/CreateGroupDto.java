package com.fzerey.user.service.group.dtos;

public class CreateGroupDto {

    public CreateGroupDto(String name) {
        this.name = name;
    }

    public CreateGroupDto() {
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
