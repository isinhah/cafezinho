package com.seucafezinho.api_seu_cafezinho.util;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.AddressResponseDto;

import java.util.UUID;

import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER_ID;

public class AddressConstants {

    public static final UUID ADDRESS_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

    public static final String STREET = "Main Street";
    public static final String NUMBER = "123";
    public static final String NEIGHBORHOOD = "Downtown";

    public static final AddressRequestDto ADDRESS_REQUEST_DTO =
            new AddressRequestDto(STREET, NUMBER, NEIGHBORHOOD);

    public static final AddressResponseDto ADDRESS_RESPONSE_DTO =
            new AddressResponseDto(ADDRESS_ID, USER_ID, STREET, NUMBER, NEIGHBORHOOD);
}