package com.fzerey.user.controller.model;

import com.fzerey.user.service.user.dtos.ListUserDto;
import com.fzerey.user.shared.requests.model.PageRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListUserModel extends PageRequest {

    private Long groupId;

    public ListUserDto toDto() {
        ListUserDto dto = new ListUserDto();
        dto.setPage(this.getPage());
        dto.setSize(this.getSize());
        dto.setSortBy(this.getSortBy());
        dto.setSortDirection(this.getSortDirection());
        dto.setQuery(this.getQuery());
        dto.setGroupId(groupId);
        return dto;
    }
}
