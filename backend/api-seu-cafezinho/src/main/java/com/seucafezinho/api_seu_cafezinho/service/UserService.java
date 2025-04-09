package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    UserResponseDto findById(UUID id);

    Page<UserResponseDto> findAll(Pageable pageable);

    UserResponseDto create(UserRequestDto createDto);

    UserResponseDto update(UUID id, UserRequestDto updateDto);

    void delete(UUID id);

    User findUserById(UUID id);

    User findByEmail(String email);
}