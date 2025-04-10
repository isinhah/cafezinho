package com.seucafezinho.api_seu_cafezinho.controller;

import com.seucafezinho.api_seu_cafezinho.service.UserService;
import com.seucafezinho.api_seu_cafezinho.util.UserConstants;
import com.seucafezinho.api_seu_cafezinho.web.controller.UserController;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetUserById_WhenSuccessful() {
        UUID userId = UserConstants.USER_ID;
        UserResponseDto userResponse = UserConstants.USER_RESPONSE_DTO;

        when(userService.findById(userId)).thenReturn(userResponse);

        assertDoesNotThrow(() -> {
            userController.getUserById(userId);
        });

        verify(userService, times(1)).findById(userId);
    }

    @Test
    void testGetAllUsers_WhenSuccessful() {
        Pageable pageable = PageRequest.of(0, 10);
        List<UserResponseDto> users = List.of(UserConstants.USER_RESPONSE_DTO);
        Page<UserResponseDto> page = new PageImpl<>(users);

        when(userService.findAll(pageable)).thenReturn(page);

        assertDoesNotThrow(() -> {
            userController.getAllUsers(pageable);
        });

        verify(userService, times(1)).findAll(pageable);
    }

    @Test
    void testAlterUser_WhenSuccessful() {
        UUID userId = UserConstants.USER_ID;
        UserRequestDto requestDto = UserConstants.USER_REQUEST_DTO;
        UserResponseDto responseDto = UserConstants.USER_RESPONSE_DTO;

        when(userService.update(userId, requestDto)).thenReturn(responseDto);

        assertDoesNotThrow(() -> {
            userController.alterUser(userId, requestDto);
        });

        verify(userService, times(1)).update(userId, requestDto);
    }

    @Test
    void testDeleteUser_WhenSuccessful() {
        UUID userId = UserConstants.USER_ID;

        doNothing().when(userService).delete(userId);

        assertDoesNotThrow(() -> {
            userController.deleteUser(userId);
        });

        verify(userService, times(1)).delete(userId);
    }
}