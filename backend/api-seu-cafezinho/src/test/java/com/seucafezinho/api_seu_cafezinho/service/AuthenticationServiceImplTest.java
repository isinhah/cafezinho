package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.security.JwtTokenService;
import com.seucafezinho.api_seu_cafezinho.service.impl.AuthenticationServiceImpl;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.TokenResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.seucafezinho.api_seu_cafezinho.util.AuthenticationConstants.AUTH_EMAIL;
import static com.seucafezinho.api_seu_cafezinho.util.AuthenticationConstants.AUTH_PASSWORD;
import static com.seucafezinho.api_seu_cafezinho.util.AuthenticationConstants.EXPIRATION_TIME;
import static com.seucafezinho.api_seu_cafezinho.util.AuthenticationConstants.LOGIN_REQUEST_DTO;
import static com.seucafezinho.api_seu_cafezinho.util.AuthenticationConstants.TOKEN;
import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER;
import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER_ID;
import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER_REQUEST_DTO;
import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER_RESPONSE_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void register_ShouldReturnTokenResponseDto_WhenSuccessful() {
        when(userService.create(USER_REQUEST_DTO)).thenReturn(USER_RESPONSE_DTO);
        when(userService.findUserById(USER_ID)).thenReturn(USER);
        when(tokenService.generateToken(USER)).thenReturn(TOKEN);
        when(tokenService.generateExpirationDate()).thenReturn(EXPIRATION_TIME);

        TokenResponseDto result = authenticationService.register(USER_REQUEST_DTO);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getToken()).isEqualTo(TOKEN);
        assertThat(result.getExpiresAt()).isEqualTo(EXPIRATION_TIME);

        verify(userService).create(USER_REQUEST_DTO);
        verify(userService).findUserById(USER_ID);
        verify(tokenService).generateToken(USER);
        verify(tokenService).generateExpirationDate();
    }

    @Test
    void login_ShouldReturnTokenResponseDto_WhenCredentialsAreValid() {
        when(userService.findByEmail(AUTH_EMAIL)).thenReturn(USER);
        when(passwordEncoder.matches(AUTH_PASSWORD, USER.getPassword())).thenReturn(true);
        when(tokenService.generateToken(USER)).thenReturn(TOKEN);
        when(tokenService.generateExpirationDate()).thenReturn(EXPIRATION_TIME);

        TokenResponseDto result = authenticationService.login(LOGIN_REQUEST_DTO);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getToken()).isEqualTo(TOKEN);
        assertThat(result.getExpiresAt()).isEqualTo(EXPIRATION_TIME);

        verify(userService).findByEmail(AUTH_EMAIL);
        verify(passwordEncoder).matches(AUTH_PASSWORD, USER.getPassword());
        verify(tokenService).generateToken(USER);
        verify(tokenService).generateExpirationDate();
    }
}
