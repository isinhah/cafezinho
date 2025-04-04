package com.seucafezinho.api_seu_cafezinho.documentation;

import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderStatusUpdateDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.CustomPageResponse;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public interface OrderControllerDocs {

    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido com base no ID fornecido")
    ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID orderId);

    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma página contendo todos os pedidos cadastrados")
    ResponseEntity<CustomPageResponse<OrderResponseDto>> getAllOrders(Pageable pageable);

    @Operation(summary = "Listar pedidos de um usuário", description = "Retorna todos os pedidos associados a um usuário específico")
    ResponseEntity<CustomPageResponse<OrderResponseDto>> getAllOrdersByUser(
            @PathVariable UUID userId, Pageable pageable);

    @Operation(summary = "Criar um novo pedido", description = "Cria um novo pedido para um usuário específico")
    ResponseEntity<OrderResponseDto> createOrder(
            @PathVariable UUID userId,
            @Valid @RequestBody OrderRequestDto createDto);

    @Operation(summary = "Atualizar pedido", description = "Modifica os dados de um pedido existente")
    ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderRequestDto updateDto);

    @Operation(summary = "Atualizar status do pedido", description = "Altera o status de um pedido existente")
    ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderStatusUpdateDto statusUpdateDto);

    @Operation(summary = "Excluir pedido", description = "Remove um pedido do sistema")
    ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId);
}