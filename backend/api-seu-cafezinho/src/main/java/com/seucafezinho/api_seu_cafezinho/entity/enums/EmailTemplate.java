package com.seucafezinho.api_seu_cafezinho.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EmailTemplate {

    WELCOME(
            "Seja bem-vindo(a) ao nosso café! ☕🍰",
            "Olá %s,\n\n" +
                    "É um prazer ter você conosco! Fique à vontade para explorar nosso cardápio e fazer seu primeiro pedido. Qual será sua escolha hoje? 😋\n\n" +
                    "Aproveite e bom apetite!"
    );

    private final String subject;
    private final String body;

    public String formatBody(String... args) {
        return String.format(body, (Object[]) args);
    }
}