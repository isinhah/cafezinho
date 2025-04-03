package com.seucafezinho.api_seu_cafezinho.producer;

import com.seucafezinho.api_seu_cafezinho.entity.User;
import com.seucafezinho.api_seu_cafezinho.web.dto.response.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String routingKey;

    public EmailProducer(RabbitTemplate rabbitTemplate, @Value("${broker.queues.email.name}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.routingKey = routingKey;
    }

    public void publishMessageEmail(User user) {
        var emailDto = new EmailDto();
        emailDto.setUserId(user.getId());
        emailDto.setEmailTo(user.getEmail());
        emailDto.setSubject("Cadastro realizado com sucesso");
        emailDto.setText(user.getName() + ", seja bem-vindo(a)! \nNós agradecemos o seu cadastro, aproveite todos os recursos da nossa plataforma!");

        rabbitTemplate.convertAndSend(routingKey, emailDto);
    }
}