package com.seucafezinho.api_seu_cafezinho.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {

    private UUID id;
    private UUID userId;
    private String street;
    private String number;
    private String neighborhood;
}