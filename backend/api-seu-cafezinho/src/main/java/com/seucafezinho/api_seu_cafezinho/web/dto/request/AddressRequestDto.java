package com.seucafezinho.api_seu_cafezinho.web.dto.request;

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
public class AddressRequestDto {

    @NotBlank(message = "Street is required")
    @Size(max = 100, message = "Street can have up to 100 characters")
    private String street;

    @NotBlank(message = "Number is required")
    @Size(max = 10, message = "Number can have up to 10 characters")
    private String number;

    @NotBlank(message = "Neighborhood is required")
    @Size(max = 50, message = "Neighborhood can have up to 50 characters")
    private String neighborhood;
}