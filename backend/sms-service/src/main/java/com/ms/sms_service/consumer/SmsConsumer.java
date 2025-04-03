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

    @RabbitListener(queues = "${broker.queue.sms.name}")
    public void consumeMessage(@Payload OrderSms sms) {
        String message = switch (sms.getStatus()) {
            case PENDING -> sms.getUserName() + ", seu pedido " + sms.getOrderId() + " está pendente.";
            case IN_PROGRESS -> sms.getUserName() + ", seu pedido " + sms.getOrderId() + " está em preparação.";
            case DELIVERING -> sms.getUserName() + ", seu pedido " + sms.getOrderId() + " está a caminho!";
            case READY_FOR_PICKUP -> sms.getUserName() + ", seu pedido " + sms.getOrderId() + " está pronto para retirada!";
            case COMPLETED -> sms.getUserName() + ", seu pedido " + sms.getOrderId() + " foi entregue. Bom apetite!";
            case CANCELED -> sms.getUserName() + ", seu pedido " + sms.getOrderId() + " foi cancelado.";
        };

        snsService.sendSms(sms.getUserPhone(), message);
    }
}