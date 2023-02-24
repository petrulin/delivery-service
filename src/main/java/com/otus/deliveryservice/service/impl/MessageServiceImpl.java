package com.otus.deliveryservice.service.impl;

import com.otus.deliveryservice.entity.Message;
import com.otus.deliveryservice.repository.MessageRepository;
import com.otus.deliveryservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("currencyService")
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message findById(String msgId) {
        return messageRepository.findById(msgId).orElse(null);
    }

}
