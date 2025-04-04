package com.seucafezinho.api_seu_cafezinho.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "DTO para enviar notificações via email")
public class EmailDto {

    @Schema(description = "Unique identifier of the user receiving the email.", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userId;

    @Schema(description = "Recipient's email address.", example = "user@example.com")
    private String emailTo;

    @Schema(description = "Email subject.", example = "Welcome to our café! ☕🍰")
    private String subject;

    @Schema(description = "Email content.", example = "Hello John, welcome to our café!")
    private String text;
}