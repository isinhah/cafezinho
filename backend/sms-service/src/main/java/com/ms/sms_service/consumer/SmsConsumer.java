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
        String message = sms.getStatus().buildMessage(sms);
        snsService.sendSms(sms.getUserPhone(), message);
    }
}