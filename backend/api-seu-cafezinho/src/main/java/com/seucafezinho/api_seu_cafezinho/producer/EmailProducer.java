package com.seucafezinho.api_seu_cafezinho.producer;

import com.seucafezinho.api_seu_cafezinho.entity.User;
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
        var emailDto = new EmailDto();
        emailDto.setUserId(user.getId());
        emailDto.setEmailTo(user.getEmail());
        emailDto.setSubject("Seja bem-vindo(a) ao nosso café! ☕🍰");
        emailDto.setText("Olá " + user.getName() + ",\n\n" +
                "É um prazer ter você conosco! Fique à vontade para explorar nosso cardápio e fazer seu primeiro pedido. Qual será sua escolha hoje? 😋\n\n" +
                "Aproveite e bom apetite!\n\n");

        rabbitTemplate.convertAndSend(routingKey, emailDto);
    }
}