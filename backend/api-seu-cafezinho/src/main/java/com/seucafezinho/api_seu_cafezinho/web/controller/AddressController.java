package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.service.AddressService;
import com.seucafezinho.api_seu_cafezinho.web.dto.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.AddressResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/{userId}/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> getByIdAndUser(
            @PathVariable UUID userId,
            @PathVariable UUID addressId) {
        AddressResponseDto address = addressService.findByIdAndUser(addressId, userId);
        return ResponseEntity.ok(address);
    }

    @GetMapping
    public ResponseEntity<Page<AddressResponseDto>> getAllByUser(
            @PathVariable UUID userId,
            Pageable pageable) {
        Page<AddressResponseDto> addresses = addressService.findAllByUser(userId, pageable);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping
    public ResponseEntity<AddressResponseDto> create(
            @PathVariable UUID userId,
            @Valid @RequestBody AddressRequestDto createDto) {
        AddressResponseDto newAddress = addressService.create(createDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> update(
            @PathVariable UUID userId,
            @PathVariable UUID addressId,
            @Valid @RequestBody AddressRequestDto updateDto) {
        AddressResponseDto existingAddress = addressService.update(userId, addressId, updateDto);
        return ResponseEntity.ok(existingAddress);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID userId,
            @PathVariable UUID addressId) {
        addressService.delete(addressId, userId);
        return ResponseEntity.noContent().build();
    }
}