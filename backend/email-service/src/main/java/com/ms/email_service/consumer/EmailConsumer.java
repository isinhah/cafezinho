package com.ms.email_service.consumer;

import com.ms.email_service.dto.EmailDto;
import com.ms.email_service.entity.Email;
import com.ms.email_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue.email.name}")
    public void listenEmailQueue(@Payload EmailDto dto) {
        var emailModel = new Email();
        BeanUtils.copyProperties(dto, emailModel);

        emailService.sendEmail(emailModel);
    }
}