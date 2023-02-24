package com.otus.deliveryservice.repository;

import com.otus.deliveryservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}
