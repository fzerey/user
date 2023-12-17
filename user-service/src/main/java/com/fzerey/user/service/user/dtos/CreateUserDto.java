package com.fzerey.user.service.user.dtos;

import java.util.List;

public class CreateUserDto{

    

    public CreateUserDto(String username, String password, Long groupId, List<UserAttributeDto> attributes) {
        this.username = username;
        this.password = password;
        this.groupId = groupId;
        this.attributes = attributes;
    }

    private String username;

    private String password;

    private Long groupId;

    private List<UserAttributeDto> attributes;

    public List<UserAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<UserAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    
}