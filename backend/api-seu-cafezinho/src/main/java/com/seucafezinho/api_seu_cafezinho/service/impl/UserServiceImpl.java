package com.seucafezinho.api_seu_cafezinho.service.impl;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.producer.EmailProducer;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.service.UserService;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.UserResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailProducer emailProducer;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponseDto findById(UUID id) {
        User user = findUserById(id);
        return UserMapper.INSTANCE.toDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper.INSTANCE::toDto);
    }

    @Transactional
    public UserResponseDto create(UserRequestDto createDto) {
        if (userRepository.existsByEmail(createDto.getEmail())) {
            throw new IllegalArgumentException("This email is already registered.");
        }

        createDto.setPassword(passwordEncoder.encode(createDto.getPassword()));

        User userToSave = UserMapper.INSTANCE.toUser(createDto);
        User savedUser = userRepository.save(userToSave);

        emailProducer.publishMessageEmail(savedUser);

        return UserMapper.INSTANCE.toDto(savedUser);
    }

    @Transactional
    public UserResponseDto update(UUID id, UserRequestDto updateDto) {
        User existingUser = findUserById(id);

        updateDto.setPassword(passwordEncoder.encode(updateDto.getPassword()));

        UserMapper.INSTANCE.updateUserFromDto(updateDto, existingUser);

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.INSTANCE.toDto(updatedUser);
    }

    @Transactional
    public void delete(UUID id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id: '%s' not found", id)));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with email: '%s' not found", email)));
    }
}