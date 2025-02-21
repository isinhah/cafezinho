package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.web.dto.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.UserResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

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
    public UserResponseDto save(UserRequestDto createDto) {
        if (userRepository.existsByEmailIgnoreCase(createDto.getEmail())) {
            throw new RuntimeException(String.format("The email: '%s' already exists", createDto.getEmail()));
        }

        User userToSave = UserMapper.INSTANCE.toUser(createDto);
        User savedUser = userRepository.save(userToSave);
        return UserMapper.INSTANCE.toDto(savedUser);
    }

    @Transactional
    public UserResponseDto update(UUID id, UserRequestDto updateDto) {
        User existingUser = findUserById(id);

        if (!existingUser.getEmail().equalsIgnoreCase(updateDto.getEmail()) &&
                userRepository.existsByEmailIgnoreCase(updateDto.getEmail())) {
            throw new RuntimeException(String.format("The email: '%s' already exists", updateDto.getEmail()));
        }

        UserMapper.INSTANCE.updateUserFromDto(updateDto, existingUser);

        User updatedUser = userRepository.save(existingUser);
        return UserMapper.INSTANCE.toDto(updatedUser);
    }

    @Transactional
    public void delete(UUID id) {
        User user = findUserById(id);

        if (!user.isActive()) {
            throw new RuntimeException(String.format("User with id: %s is already inactive", id));
        }

        user.setActive(false);
        userRepository.save(user);
    }

    @Transactional
    public UserResponseDto reactivate(UUID id) {
        User user = findUserById(id);

        if (user.isActive()) {
            throw new RuntimeException(String.format("User with id: '%s' is already active", id));
        }

        user.setActive(true);
        User reactivatedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(reactivatedUser);
    }

    @Transactional(readOnly = true)
    private User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("User with id: '%s' not found", id)));
    }
}