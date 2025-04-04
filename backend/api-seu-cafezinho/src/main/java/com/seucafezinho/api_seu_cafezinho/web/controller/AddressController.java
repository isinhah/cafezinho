package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.documentation.AddressControllerDocs;
import com.seucafezinho.api_seu_cafezinho.service.AddressService;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.AddressResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CustomPageResponse;
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
@RequestMapping("/api/v1/addresses")
public class AddressController implements AddressControllerDocs {

    private final AddressService addressService;

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable UUID addressId) {
        AddressResponseDto address = addressService.findById(addressId);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CustomPageResponse<AddressResponseDto>> getAddressesByUser(
            @PathVariable UUID userId, Pageable pageable) {
        Page<AddressResponseDto> page = addressService.findAllByUser(userId, pageable);
        return ResponseEntity.ok(new CustomPageResponse<>(page));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressResponseDto> createAddress(
            @PathVariable UUID userId, @Valid @RequestBody AddressRequestDto createDto) {
        AddressResponseDto newAddress = addressService.create(createDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable UUID addressId, @Valid @RequestBody AddressRequestDto updateDto) {
        AddressResponseDto updatedAddress = addressService.update(addressId, updateDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID addressId) {
        addressService.delete(addressId);
        return ResponseEntity.noContent().build();
    }
}