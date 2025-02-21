package com.seucafezinho.api_seu_cafezinho.web.controller;

import com.seucafezinho.api_seu_cafezinho.service.UserService;
import com.seucafezinho.api_seu_cafezinho.web.dto.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.UserResponseDto;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable UUID id) {
        UserResponseDto response = userService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public Page<UserResponseDto> getAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserRequestDto createDto) {
        UserResponseDto newUserResponse = userService.save(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> alter(
            @PathVariable UUID id,
            @Valid @RequestBody UserRequestDto updateDto) {
        UserResponseDto existingUser = userService.update(id, updateDto);
        return ResponseEntity.ok(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reactivate")
    public ResponseEntity<UserResponseDto> reactivate(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.reactivate(id));
    }
}