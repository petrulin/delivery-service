package com.otus.deliveryservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.deliveryservice.entity.Message;
import com.otus.deliveryservice.entity.CourierSchedule;
import com.otus.deliveryservice.rabbitmq.domain.RMessage;
import com.otus.deliveryservice.rabbitmq.domain.dto.BookingCourierDTO;
import com.otus.deliveryservice.service.CourierService;
import com.otus.deliveryservice.service.MessageService;
import com.otus.deliveryservice.service.CourierScheduleService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitListener {

    private final MessageService messageService;
    private final CourierService courierService;
    private final RabbitTemplate rt;

    @Value("${spring.rabbitmq.queues.order-answer-queue}")
    private String orderAnswerQueue;
    @Value("${spring.rabbitmq.exchanges.order-answer-exchange}")
    private String orderAnswerExchange;

    @Transactional
    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "${spring.rabbitmq.queues.delivery-queue}", ackMode = "MANUAL")
    public void orderQueueListener(RMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            var om = new ObjectMapper();
            var msg = messageService.findById(message.getMsgId());
            if (msg == null) {
                messageService.save(new Message(message.getMsgId()));
                switch (message.getCmd()) {
                    case "bookingCourier" ->  {
                        om.getTypeFactory().constructCollectionType(ArrayList.class, BookingCourierDTO.class);
                        var bookingCourierMsg = om.convertValue(message.getMessage(), BookingCourierDTO.class);
                        var answer = courierService.bookingCourier(bookingCourierMsg);
                        rt.convertAndSend(orderAnswerExchange, orderAnswerQueue,
                                new RMessage(UUID.randomUUID().toString(), "deliveryAnswer", answer)
                        );
                    }
                    default -> log.warn("::OrderService:: rabbitmq listener method. Unknown message type");
                }
            }
            else {
                log.warn("::OrderService:: rabbitmq listener method orderQueueListener duplicate message!");
            }
        } catch (Exception ex) {
            log.error("::OrderService:: rabbitmq listener method orderQueueListener with error message {}", ex.getLocalizedMessage());
            log.error("::OrderService:: rabbitmq listener method orderQueueListener with stackTrace {}", ExceptionUtils.getStackTrace(ex));
        } finally {
            basicAck(channel, tag, true);
        }
    }

    private void basicAck(Channel channel, Long tag, boolean b) {
        try {
            channel.basicAck(tag, b);
        } catch (IOException ex) {
            log.error("::OrderService:: rabbitmq listener method basicAck with stackTrace {}", ExceptionUtils.getStackTrace(ex));
        }
    }
}
