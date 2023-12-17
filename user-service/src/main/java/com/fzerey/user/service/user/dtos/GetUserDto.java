package com.fzerey.user.service.user.dtos;

import java.util.Set;


public class GetUserDto {
    
    private Long id;
    private String username;
    private Set<UserAttributeDto> attributes;
    
    public GetUserDto(Long id, String username, Set<UserAttributeDto> attributes) {
        this.id = id;
        this.username = username;
        this.attributes = attributes;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Set<UserAttributeDto> getAttributes() {
        return attributes;
    }
    public void setAttributes(Set<UserAttributeDto> attributes) {
        this.attributes = attributes;
    }
    

}
