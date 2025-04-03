package com.ms.sms_service.consumer;

import com.ms.sms_service.dto.OrderSms;
import com.ms.sms_service.service.SnsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SmsConsumer {

    private final SnsService snsService;

    @RabbitListener(queues = "${spring.rabbitmq.queue.sms.name}")
    public void consumeMessage(@Payload OrderSms sms) {
        String message = switch (sms.getStatus()) {
            case PENDING -> sms.getUserName() + ", recebemos seu pedido " + sms.getOrderId() + " e ele está aguardando confirmação.";
            case IN_PROGRESS -> sms.getUserName() + ", estamos preparando seu pedido " + sms.getOrderId();
            case DELIVERING -> sms.getUserName() + ", seu pedido " + sms.getOrderId() + " está a caminho.";
            case READY_FOR_PICKUP -> sms.getUserName() + ", seu pedido " + sms.getOrderId() + " está pronto para retirada.";
            case COMPLETED -> sms.getUserName() + ", seu pedido " + sms.getOrderId() + " foi entregue.";
            case CANCELED -> sms.getUserName() + ", seu pedido " + sms.getOrderId() + " foi cancelado. Se precisar, estamos aqui para ajudar.";
        };

        snsService.sendSms(sms.getUserPhone(), message);
    }
}