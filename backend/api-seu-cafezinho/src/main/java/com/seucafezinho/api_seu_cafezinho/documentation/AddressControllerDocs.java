package com.seucafezinho.api_seu_cafezinho.documentation;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.AddressResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CustomPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "Endereços", description = "Endpoints para gerenciamento de endereços")
public interface AddressControllerDocs {

    @Operation(summary = "Buscar endereço por ID", description = "Retorna os detalhes de um endereço específico")
    ResponseEntity<AddressResponseDto> getAddressById(
            @Parameter(description = "ID do endereço", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID addressId
    );

    @Operation(summary = "Listar endereços por usuário", description = "Retorna uma lista paginada de endereços associados a um usuário")
    ResponseEntity<CustomPageResponse<AddressResponseDto>> getAddressesByUser(
            @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-556642440000")
            @PathVariable UUID userId,
            Pageable pageable
    );

    @Operation(summary = "Criar novo endereço", description = "Cria um novo endereço para um usuário")
    ResponseEntity<AddressResponseDto> createAddress(
            @Parameter(description = "ID do usuário", example = "123e4567-e89b-12d3-a456-556642440000")
            @PathVariable UUID userId,

            @RequestBody AddressRequestDto createDto
    );

    @Operation(summary = "Atualizar endereço", description = "Atualiza as informações de um endereço existente")
    ResponseEntity<AddressResponseDto> updateAddress(
            @Parameter(description = "ID do endereço", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID addressId,

            @RequestBody AddressRequestDto updateDto
    );

    @Operation(summary = "Excluir endereço", description = "Remove um endereço do sistema")
    ResponseEntity<Void> deleteAddress(
            @Parameter(description = "ID do endereço", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID addressId
    );
}