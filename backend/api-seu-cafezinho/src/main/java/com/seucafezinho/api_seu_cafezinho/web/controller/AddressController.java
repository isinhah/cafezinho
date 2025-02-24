package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.service.AddressService;
import com.seucafezinho.api_seu_cafezinho.web.dto.AddressRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.AddressResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/{userId}/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<Page<AddressResponseDto>> getAllByUser(
            @PathVariable UUID userId,
            User user,
            Pageable pageable) {

        Page<AddressResponseDto> addresses = addressService.findAllByUser(user, pageable);
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getByIdAndUser(
            @PathVariable UUID userId,
            @PathVariable UUID id, User user) {

        AddressResponseDto address = addressService.findByIdAndUser(id, user);
        return ResponseEntity.ok(address);
    }

    // create

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> alter(
            @PathVariable UUID userId,
            @PathVariable UUID id,
            @RequestBody AddressRequestDto requestDto,
            User user) {

        AddressResponseDto updatedAddress = addressService.update(id, requestDto, user);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID userId, @PathVariable UUID id, User user) {
        addressService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}