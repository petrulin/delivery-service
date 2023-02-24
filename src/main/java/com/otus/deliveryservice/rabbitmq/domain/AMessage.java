package com.otus.deliveryservice.rabbitmq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class AMessage {

    protected String msgId;
    protected String cmd;

    protected Object message;

}
