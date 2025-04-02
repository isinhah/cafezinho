package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.Address;
import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.AddressResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AddressService {

    AddressResponseDto findById(UUID addressId);

    Page<AddressResponseDto> findAllByUser(UUID userId, Pageable pageable);

    AddressResponseDto create(AddressRequestDto createDto, UUID userId);

    AddressResponseDto update(UUID addressId, AddressRequestDto updateDto);

    void delete(UUID addressId);

    Address findAddressById(UUID addressId);

    User findUserById(UUID userId);
}