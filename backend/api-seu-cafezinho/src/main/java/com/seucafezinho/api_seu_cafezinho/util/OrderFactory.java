package com.seucafezinho.api_seu_cafezinho.util;

import com.seucafezinho.api_seu_cafezinho.entity.*;
import com.seucafezinho.api_seu_cafezinho.entity.enums.DeliveryMethod;
import com.seucafezinho.api_seu_cafezinho.entity.enums.OrderStatus;
import com.seucafezinho.api_seu_cafezinho.entity.enums.PaymentMethod;
import com.seucafezinho.api_seu_cafezinho.service.impl.AddressServiceImpl;
import com.seucafezinho.api_seu_cafezinho.service.impl.ProductServiceImpl;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderFactory {

    private final ProductServiceImpl productService;
    private final AddressServiceImpl addressServiceImpl;

    /**
     * Cria um pedido a partir do DTO.
     */
    public Order createOrder(User user, OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setDeliveryMethod(DeliveryMethod.valueOf(orderRequestDto.getDeliveryMethod()));
        order.setPaymentMethod(PaymentMethod.valueOf(orderRequestDto.getPaymentMethod()));
        order.setTotalPrice(BigDecimal.ZERO);

        setOrderDelivery(order, orderRequestDto);
        order.setOrderItems(convertOrderItems(order, orderRequestDto));

        return order;
    }

    /**
     * Atualiza um pedido existente com base no DTO.
     */
    public void updateOrder(Order order, OrderRequestDto orderRequestDto) {
        order.setDeliveryMethod(DeliveryMethod.valueOf(orderRequestDto.getDeliveryMethod()));
        order.setPaymentMethod(PaymentMethod.valueOf(orderRequestDto.getPaymentMethod()));

        setOrderDelivery(order, orderRequestDto);
        order.setOrderItems(convertOrderItems(order, orderRequestDto));
    }

    /**
     * Configura o endereço de entrega.
     */
    private void setOrderDelivery(Order order, OrderRequestDto orderRequestDto) {
        if ("HOME_DELIVERY".equalsIgnoreCase(orderRequestDto.getDeliveryMethod())) {
            Address address = addressServiceImpl.findAddressById(orderRequestDto.getAddressId());
            order.setAddress(address);
        }
    }

    /**
     * Converte os itens do pedido do DTO para a entidade.
     */
    private List<OrderItem> convertOrderItems(Order order, OrderRequestDto orderRequestDto) {
        return orderRequestDto.getProducts().stream()
                .map(itemDto -> {
                    Product product = productService.findProductById(itemDto.getProductId());
                    return OrderItem.builder()
                            .product(product)
                            .productQuantity(itemDto.getProductQuantity())
                            .unitPrice(product.getPrice())
                            .order(order)
                            .build();
                })
                .collect(Collectors.toList());
    }
}