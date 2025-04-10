package com.seucafezinho.api_seu_cafezinho.documentation;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.LoginRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.TokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Autenticação", description = "Endpoints para autenticação dos usuários e administradores")
public interface AuthenticationControllerDocs {

    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário e retorna um token JWT.")
    ResponseEntity<TokenResponseDto> register(
            @Parameter(description = "Dados do novo usuário para registro")
            @RequestBody UserRequestDto dto
    );

    @Operation(summary = "Autenticar usuário", description = "Autentica um usuário existente e retorna um token JWT.")
    ResponseEntity<TokenResponseDto> login(
            @Parameter(description = "Credenciais do usuário para login")
            @RequestBody LoginRequestDto dto
    );
}