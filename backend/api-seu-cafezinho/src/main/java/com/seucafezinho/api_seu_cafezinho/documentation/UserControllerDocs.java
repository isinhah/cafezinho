package com.seucafezinho.api_seu_cafezinho.documentation;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.UserRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CustomPageResponse;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public interface UserControllerDocs {

    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário com base no ID fornecido")
    ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id);

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma página contendo todos os usuários cadastrados")
    ResponseEntity<CustomPageResponse<UserResponseDto>> getAllUsers(Pageable pageable);

    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário no sistema")
    ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto createDto);

    @Operation(summary = "Atualizar usuário", description = "Modifica os dados de um usuário existente")
    ResponseEntity<UserResponseDto> alterUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserRequestDto updateDto);

    @Operation(summary = "Excluir usuário", description = "Remove um usuário do sistema")
    ResponseEntity<Void> deleteUser(@PathVariable UUID id);
}