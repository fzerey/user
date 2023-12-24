package com.fzerey.user.shared.requests.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private int page;
    private int totalPage;
    private int size;
    private Long totalElements;
    private List<T> data;

    public void fromPage(Page<T> page) {
        this.page = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPage = (int) Math.ceil((double) totalElements / page.getSize());
        this.data = page.getContent();
    }
}
