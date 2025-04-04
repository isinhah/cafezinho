package com.seucafezinho.api_seu_cafezinho.documentation;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.CategoryRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CategoryResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CustomPageResponse;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.ProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias de produtos")
public interface CategoryControllerDocs {

    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria com base no ID fornecido")
    ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id);

    @Operation(summary = "Listar todas as categorias", description = "Retorna uma página contendo todas as categorias cadastradas")
    ResponseEntity<CustomPageResponse<CategoryResponseDto>> getAllCategories(Pageable pageable);

    @Operation(summary = "Buscar categoria por nome", description = "Retorna uma categoria pelo nome informado")
    ResponseEntity<CategoryResponseDto> getCategoryByName(@RequestParam String name);

    @Operation(summary = "Listar produtos de uma categoria", description = "Retorna todos os produtos associados a uma determinada categoria")
    ResponseEntity<CustomPageResponse<ProductResponseDto>> getProductsByCategory(
            @PathVariable Long categoryId, Pageable pageable);

    @Operation(summary = "Adicionar um produto a uma categoria", description = "Associa um produto a uma categoria específica")
    ResponseEntity<ProductResponseDto> addProductToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId);

    @Operation(summary = "Criar uma nova categoria", description = "Adiciona uma nova categoria ao sistema")
    ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto createDto);

    @Operation(summary = "Atualizar categoria", description = "Modifica os dados de uma categoria existente")
    ResponseEntity<CategoryResponseDto> alterCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDto updateDto);

    @Operation(summary = "Excluir categoria", description = "Remove uma categoria do sistema")
    ResponseEntity<Void> deleteCategory(@PathVariable Long id);
}