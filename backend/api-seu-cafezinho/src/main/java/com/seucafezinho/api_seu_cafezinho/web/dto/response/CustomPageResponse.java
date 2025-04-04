package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "Representa uma resposta paginada personalizada contendo uma lista de itens")
public record CustomPageResponse<T>(

        @Schema(description = "List of items in the current page.", example = "[...]")
        List<T> content,

        @Schema(description = "Total number of elements across all pages.", example = "150")
        int totalElements,

        @Schema(description = "Total number of pages available.", example = "15")
        int totalPages,

        @Schema(description = "Number of elements per page.", example = "10")
        int size,

        @Schema(description = "Current page number (zero-based index).", example = "0")
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