package com.seucafezinho.api_seu_cafezinho.controller;

import com.seucafezinho.api_seu_cafezinho.service.AuthenticationService;
import com.seucafezinho.api_seu_cafezinho.web.controller.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.seucafezinho.api_seu_cafezinho.util.AuthenticationConstants.LOGIN_REQUEST_DTO;
import static com.seucafezinho.api_seu_cafezinho.util.AuthenticationConstants.TOKEN_RESPONSE_DTO;
import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER_REQUEST_DTO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void testRegister_WhenSuccessful() {
        when(authenticationService.register(USER_REQUEST_DTO)).thenReturn(TOKEN_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            authenticationController.register(USER_REQUEST_DTO);
        });

        verify(authenticationService, times(1)).register(USER_REQUEST_DTO);
    }

    @Test
    void testLogin_WhenSuccessful() {
        when(authenticationService.login(LOGIN_REQUEST_DTO)).thenReturn(TOKEN_RESPONSE_DTO);

        assertDoesNotThrow(() -> {
            authenticationController.login(LOGIN_REQUEST_DTO);
        });

        verify(authenticationService, times(1)).login(LOGIN_REQUEST_DTO);
    }
}