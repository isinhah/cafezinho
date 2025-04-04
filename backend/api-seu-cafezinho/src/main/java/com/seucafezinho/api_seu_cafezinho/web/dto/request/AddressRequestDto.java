package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação ou atualização de um endereço")
public class AddressRequestDto {

    @NotBlank(message = "Street is required")
    @Size(max = 100, message = "Street can have up to 100 characters")
    @Schema(description = "Street name of the address.", example = "Main Street")
    private String street;

    @NotBlank(message = "Number is required")
    @Size(max = 10, message = "Number can have up to 10 characters")
    @Schema(description = "House or building number of the address.", example = "123")
    private String number;

    @NotBlank(message = "Neighborhood is required")
    @Size(max = 50, message = "Neighborhood can have up to 50 characters")
    @Schema(description = "Neighborhood or district of the address.", example = "Downtown")
    private String neighborhood;

}