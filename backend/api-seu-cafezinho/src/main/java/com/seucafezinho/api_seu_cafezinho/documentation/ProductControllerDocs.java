package com.seucafezinho.api_seu_cafezinho.documentation;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.ProductRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CustomPageResponse;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.ProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public interface ProductControllerDocs {

    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto com base no ID fornecido")
    ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId);

    @Operation(summary = "Listar todos os produtos", description = "Retorna uma página contendo todos os produtos cadastrados")
    ResponseEntity<CustomPageResponse<ProductResponseDto>> getAllProducts(Pageable pageable);

    @Operation(summary = "Criar um novo produto", description = "Cria um novo produto no sistema")
    ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody ProductRequestDto createDto);

    @Operation(summary = "Atualizar produto", description = "Modifica os dados de um produto existente")
    ResponseEntity<ProductResponseDto> alterProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequestDto updateDto);

    @Operation(summary = "Excluir produto", description = "Remove um produto do sistema")
    ResponseEntity<Void> deleteProduct(@PathVariable Long productId);
}