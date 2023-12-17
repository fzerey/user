package com.fzerey.user.service.user.dtos;

public class UpdateUserDto {
    public UpdateUserDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    private Long id;

    private String username;

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
}
