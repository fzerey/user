package com.fzerey.user.controller.model;

import com.fzerey.user.service.group.dtos.ListGroupDto;
import com.fzerey.user.shared.requests.model.PageRequest;

public class ListGroupModel extends PageRequest {
    public ListGroupDto toDto() {
        ListGroupDto dto = new ListGroupDto();
        dto.setPage(this.getPage());
        dto.setSize(this.getSize());
        dto.setSortBy(this.getSortBy());
        dto.setSortDirection(this.getSortDirection());
        dto.setQuery(this.getQuery());
        return dto;
    }
}
