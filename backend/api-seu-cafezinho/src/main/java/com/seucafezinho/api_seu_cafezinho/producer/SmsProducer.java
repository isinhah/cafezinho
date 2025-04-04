package com.seucafezinho.api_seu_cafezinho.producer;

import com.seucafezinho.api_seu_cafezinho.entity.Order;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.OrderSmsDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String routingKey;

    public SmsProducer(RabbitTemplate rabbitTemplate, @Value("${spring.rabbitmq.queues.sms.name}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.routingKey = routingKey;
    }

    public void publishOrderSms(Order order) {
        var orderSmsDto = new OrderSmsDto();
        orderSmsDto.setOrderId(order.getId());
        orderSmsDto.setUserName(order.getUser().getName());
        orderSmsDto.setUserPhone(order.getUser().getPhone());
        orderSmsDto.setStatus(order.getStatus());

        rabbitTemplate.convertAndSend(routingKey, orderSmsDto);
    }
}
