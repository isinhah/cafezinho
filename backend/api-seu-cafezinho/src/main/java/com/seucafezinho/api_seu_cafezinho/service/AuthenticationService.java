package com.seucafezinho.api_seu_cafezinho.service;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.LoginRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.TokenResponseDto;

public interface AuthenticationService {

    TokenResponseDto register(UserRequestDto dto);

    TokenResponseDto login(LoginRequestDto dto);
}
