package com.seucafezinho.api_seu_cafezinho.producer;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.entity.enums.EmailTemplate;
import com.seucafezinho.api_seu_cafezinho.web.dto.request.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String routingKey;

    public EmailProducer(RabbitTemplate rabbitTemplate, @Value("${spring.rabbitmq.queues.email.name}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.routingKey = routingKey;
    }

    public void publishMessageEmail(User user) {
        EmailTemplate template = EmailTemplate.WELCOME;

        var emailDto = new EmailDto();
        emailDto.setUserId(user.getId());
        emailDto.setEmailTo(user.getEmail());
        emailDto.setSubject(template.getSubject());
        emailDto.setText(template.formatBody(user.getName()));

        rabbitTemplate.convertAndSend(routingKey, emailDto);
    }
}