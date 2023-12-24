package com.fzerey.user.shared.requests.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private String query;

}
