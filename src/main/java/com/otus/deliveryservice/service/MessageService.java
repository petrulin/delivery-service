package com.otus.deliveryservice.service;

import com.otus.deliveryservice.entity.Message;


public interface MessageService {

    Message save(Message user);
    Message findById(String msgId);

}
