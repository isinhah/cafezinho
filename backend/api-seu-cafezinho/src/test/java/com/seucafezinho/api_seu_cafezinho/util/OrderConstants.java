package com.seucafezinho.api_seu_cafezinho.util;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.entity.OrderItem;
import com.seucafezinho.api_seu_cafezinho.entity.enums.DeliveryMethod;
import com.seucafezinho.api_seu_cafezinho.entity.enums.OrderStatus;
import com.seucafezinho.api_seu_cafezinho.entity.enums.PaymentMethod;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderItemRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderStatusUpdateDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderItemResponseDto;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.OrderResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.seucafezinho.api_seu_cafezinho.util.AddressConstants.ADDRESS;
import static com.seucafezinho.api_seu_cafezinho.util.ProductConstants.PRODUCT;
import static com.seucafezinho.api_seu_cafezinho.util.ProductConstants.PRODUCT_ID;
import static com.seucafezinho.api_seu_cafezinho.util.ProductConstants.PRODUCT_NAME;
import static com.seucafezinho.api_seu_cafezinho.util.ProductConstants.PRODUCT_PRICE;
import static com.seucafezinho.api_seu_cafezinho.util.UserConstants.USER;

public class OrderConstants {

    public static final UUID ORDER_ID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    public static final String DELIVERY_METHOD = "HOME_DELIVERY";
    public static final String PAYMENT_METHOD = "PIX";
    public static final String ORDER_STATUS = "COMPLETED";
    public static final Integer PRODUCT_QUANTITY = 2;

    public static final OrderItemRequestDto ORDER_ITEM_REQUEST_DTO = new OrderItemRequestDto(
            PRODUCT_ID,
            PRODUCT_QUANTITY
    );

    public static final OrderRequestDto ORDER_REQUEST_DTO = new OrderRequestDto(
            DELIVERY_METHOD,
            ADDRESS.getId(),
            List.of(ORDER_ITEM_REQUEST_DTO),
            PAYMENT_METHOD
    );

    public static final OrderItemResponseDto ORDER_ITEM_RESPONSE_DTO = new OrderItemResponseDto(
            PRODUCT_ID,
            PRODUCT_NAME,
            PRODUCT_QUANTITY,
            PRODUCT_PRICE
    );

    public static final OrderResponseDto ORDER_RESPONSE_DTO = new OrderResponseDto(
            ORDER_ID,
            ADDRESS.getId(),
            DELIVERY_METHOD,
            PAYMENT_METHOD,
            USER.getName(),
            USER.getPhone(),
            ORDER_STATUS,
            PRODUCT_PRICE.multiply(BigDecimal.valueOf(PRODUCT_QUANTITY)),
            List.of(ORDER_ITEM_RESPONSE_DTO)
    );

    public static final Order ORDER = Order.builder()
            .id(ORDER_ID)
            .user(USER)
            .address(ADDRESS)
            .deliveryMethod(DeliveryMethod.HOME_DELIVERY)
            .paymentMethod(PaymentMethod.PIX)
            .status(OrderStatus.COMPLETED)
            .build();

    public static final OrderItem ORDER_ITEM = OrderItem.builder()
            .id(1L)
            .order(ORDER)
            .product(PRODUCT)
            .productQuantity(PRODUCT_QUANTITY)
            .unitPrice(PRODUCT_PRICE)
            .build();

    static {
        ORDER.setOrderItems(List.of(ORDER_ITEM));
        ORDER.calculateTotalPrice();
    }

    public static final OrderStatusUpdateDto STATUS_UPDATE_DTO = new OrderStatusUpdateDto(
            ORDER_STATUS
    );
}
