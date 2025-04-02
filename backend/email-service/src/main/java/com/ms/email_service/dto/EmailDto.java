package com.ms.email_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailDto {

    private UUID userId;
    private String emailTo;
    private String subject;
    private String text;
}