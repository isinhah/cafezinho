package com.ms.sms_service.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SnsService {

    private final AmazonSNS amazonSNS;

    @Transactional
    public void sendSms(String phone, String message) {
        try {
            PublishRequest publishRequest = new PublishRequest()
                    .withMessage(message)
                    .withPhoneNumber(phone);
            amazonSNS.publish(publishRequest);

            log.info("SMS enviado para {}: {}", phone, message);
        } catch (Exception e) {
            log.error("Erro ao enviar SMS para {}: {}", phone, e.getMessage());
            throw new RuntimeException("Erro ao enviar SMS: " + e.getMessage(), e);
        }
    }
}