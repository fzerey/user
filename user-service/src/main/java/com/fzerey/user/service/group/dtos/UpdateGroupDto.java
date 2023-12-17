package com.fzerey.user.service.group.dtos;

public class UpdateGroupDto {

    public UpdateGroupDto(String name) {
        this.name = name;
    }

    private Long id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
