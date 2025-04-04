package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar um endereço associado a um usuário")
public class AddressResponseDto {

    @Schema(description = "Unique identifier of the address", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Unique identifier of the user associated with this address", example = "550e8400-e29b-41d4-a716-446655440001")
    private UUID userId;

    @Schema(description = "Street name of the address", example = "Main Street")
    private String street;

    @Schema(description = "House or apartment number", example = "123")
    private String number;

    @Schema(description = "Neighborhood where the address is located", example = "Downtown")
    private String neighborhood;
}