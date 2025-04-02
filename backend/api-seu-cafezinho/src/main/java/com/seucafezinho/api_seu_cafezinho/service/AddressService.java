package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.AddressResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AddressService {

    AddressResponseDto findByIdAndUser(UUID userId, UUID addressId);

    Page<AddressResponseDto> findAllByUser(UUID userId, Pageable pageable);

    AddressResponseDto create(AddressRequestDto createDto, UUID userId);

    AddressResponseDto update(UUID userId, UUID addressId, AddressRequestDto updateDto);

    void delete(UUID userId, UUID addressId);
}