package com.ms.sms_service.enums;

import com.ms.sms_service.dto.OrderSms;

public enum OrderStatus {
    PENDING {
        @Override
        public String buildMessage(OrderSms sms) {
            return String.format("%s, recebemos seu pedido %s e ele está aguardando confirmação.",
                    sms.getUserName(), sms.getOrderId());
        }
    },
    IN_PROGRESS {
        @Override
        public String buildMessage(OrderSms sms) {
            return String.format("%s, estamos preparando seu pedido %s.",
                    sms.getUserName(), sms.getOrderId());
        }
    },
    DELIVERING {
        @Override
        public String buildMessage(OrderSms sms) {
            return String.format("%s, seu pedido %s está a caminho.",
                    sms.getUserName(), sms.getOrderId());
        }
    },
    READY_FOR_PICKUP {
        @Override
        public String buildMessage(OrderSms sms) {
            return String.format("%s, seu pedido %s está pronto para retirada.",
                    sms.getUserName(), sms.getOrderId());
        }
    },
    COMPLETED {
        @Override
        public String buildMessage(OrderSms sms) {
            return String.format("%s, seu pedido %s foi entregue.",
                    sms.getUserName(), sms.getOrderId());
        }
    },
    CANCELED {
        @Override
        public String buildMessage(OrderSms sms) {
            return String.format("%s, seu pedido %s foi cancelado. Se precisar, estamos aqui para ajudar.",
                    sms.getUserName(), sms.getOrderId());
        }
    };

    public abstract String buildMessage(OrderSms sms);
}