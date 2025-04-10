package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.producer.EmailProducer;
import com.seucafezinho.api_seu_cafezinho.repository.UserRepository;
import com.seucafezinho.api_seu_cafezinho.service.impl.UserServiceImpl;
import com.seucafezinho.api_seu_cafezinho.util.SecurityContextTestUtil;
import com.seucafezinho.api_seu_cafezinho.util.UserConstants;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.UserResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailProducer emailProducer;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findById_ShouldReturnUserResponseDto_WhenSuccessful() {
        SecurityContextTestUtil.mockAuthenticatedUser(UserConstants.USER_ID.toString());

        User user = UserMapper.INSTANCE.toUser(UserConstants.USER_REQUEST_DTO);
        user.setId(UserConstants.USER_ID);

        when(userRepository.findById(UserConstants.USER_ID))
                .thenReturn(Optional.of(user));

        UserResponseDto response = userService.findById(UserConstants.USER_ID);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(UserConstants.USER_RESPONSE_DTO);

        verify(userRepository).findById(UserConstants.USER_ID);

        SecurityContextTestUtil.clear();
    }

    @Test
    void findById_ShouldThrowEntityNotFoundException_WhenUserNotFound() {
        SecurityContextTestUtil.mockAuthenticatedUser(UserConstants.USER_ID.toString());

        when(userRepository.findById(UserConstants.USER_ID))
                .thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () ->
                userService.findById(UserConstants.USER_ID));

        SecurityContextTestUtil.clear();
    }

    @Test
    void findAll_ShouldReturnPageOfUserResponseDto_WhenSuccessful() {
        User user = UserMapper.INSTANCE.toUser(UserConstants.USER_REQUEST_DTO);
        user.setId(UserConstants.USER_ID);
        PageRequest pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(user));

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<UserResponseDto> result = userService.findAll(pageable);

        assertThat(result.getContent())
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(UserConstants.USER_RESPONSE_DTO);

        verify(userRepository).findAll(pageable);
    }

    @Test
    void create_ShouldSaveAndReturnUserResponseDto_WhenSuccessful() {
        User userToSave = UserMapper.INSTANCE.toUser(UserConstants.USER_REQUEST_DTO);
        User savedUser = UserMapper.INSTANCE.toUser(UserConstants.USER_REQUEST_DTO);
        savedUser.setId(UserConstants.USER_ID);

        when(userRepository.existsByEmail(UserConstants.USER_EMAIL)).thenReturn(false);
        when(passwordEncoder.encode(UserConstants.USER_PASSWORD)).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDto response = userService.create(UserConstants.USER_REQUEST_DTO);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(UserConstants.USER_RESPONSE_DTO);

        verify(userRepository).save(any(User.class));
        verify(emailProducer).publishMessageEmail(savedUser);
    }

    @Test
    void create_ShouldThrowIllegalArgumentException_WhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(UserConstants.USER_EMAIL)).thenReturn(true);

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () ->
                userService.create(UserConstants.USER_REQUEST_DTO));

        verify(userRepository).existsByEmail(UserConstants.USER_EMAIL);
    }

    @Test
    void update_ShouldReturnUserResponseDto_WhenSuccessful() {
        SecurityContextTestUtil.mockAuthenticatedUser(UserConstants.USER_ID.toString());

        User existingUser = UserMapper.INSTANCE.toUser(UserConstants.USER_REQUEST_DTO);
        existingUser.setId(UserConstants.USER_ID);

        when(userRepository.findById(UserConstants.USER_ID)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        UserResponseDto response = userService.update(UserConstants.USER_ID, UserConstants.USER_REQUEST_DTO);

        assertNotNull(response);
        assertEquals(UserConstants.USER_NAME, response.getName());

        verify(userRepository).findById(UserConstants.USER_ID);
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(User.class));

        SecurityContextTestUtil.clear();
    }

    @Test
    void update_ShouldThrowEntityNotFoundException_WhenUserNotFound() {
        SecurityContextTestUtil.mockAuthenticatedUser(UserConstants.USER_ID.toString());

        when(userRepository.findById(UserConstants.USER_ID))
                .thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () ->
                userService.update(UserConstants.USER_ID, UserConstants.USER_REQUEST_DTO));

        SecurityContextTestUtil.clear();
    }

    @Test
    void delete_ShouldRemoveUser_WhenSuccessful() {
        SecurityContextTestUtil.mockAuthenticatedUser(UserConstants.USER_ID.toString());

        User user = UserMapper.INSTANCE.toUser(UserConstants.USER_REQUEST_DTO);
        user.setId(UserConstants.USER_ID);

        when(userRepository.findById(UserConstants.USER_ID))
                .thenReturn(Optional.of(user));

        userService.delete(UserConstants.USER_ID);

        verify(userRepository).delete(user);

        SecurityContextTestUtil.clear();
    }

    @Test
    void delete_ShouldThrowEntityNotFoundException_WhenUserNotFound() {
        SecurityContextTestUtil.mockAuthenticatedUser(UserConstants.USER_ID.toString());

        when(userRepository.findById(UserConstants.USER_ID))
                .thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () ->
                userService.delete(UserConstants.USER_ID));

        SecurityContextTestUtil.clear();
    }
}