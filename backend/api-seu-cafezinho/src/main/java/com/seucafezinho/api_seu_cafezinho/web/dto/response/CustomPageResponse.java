package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record CustomPageResponse<T>(
        List<T> content,
        int totalElements,
        int totalPages,
        int size,
        int number
) {
    public CustomPageResponse(Page<T> page) {
        this(
                page.getContent(),
                (int) page.getTotalElements(),
                page.getTotalPages(),
                page.getSize(),
                page.getNumber()
        );
    }
}