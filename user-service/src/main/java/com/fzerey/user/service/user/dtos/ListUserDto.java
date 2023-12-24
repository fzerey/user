package com.fzerey.user.service.user.dtos;

import com.fzerey.user.shared.requests.model.PageRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListUserDto extends PageRequest {
    private Long groupId;
}
