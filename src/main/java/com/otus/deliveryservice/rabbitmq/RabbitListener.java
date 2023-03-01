package com.otus.deliveryservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.deliveryservice.entity.Message;
import com.otus.deliveryservice.rabbitmq.domain.RMessage;
import com.otus.deliveryservice.rabbitmq.domain.dto.CancelDTO;
import com.otus.deliveryservice.rabbitmq.domain.dto.TrxDTO;
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
    private final CourierScheduleService courierScheduleService;
    private final RabbitTemplate rt;

    @Value("${spring.rabbitmq.queues.service-answer-queue}")
    private String serviceAnswerQueue;
    @Value("${spring.rabbitmq.exchanges.service-answer-exchange}")
    private String serviceAnswerExchange;

    @Value("${spring.rabbitmq.queues.pay-queue}")
    private String payQueue;
    @Value("${spring.rabbitmq.exchanges.pay-exchange}")
    private String payExchange;

    @Value("${spring.rabbitmq.queues.store-queue}")
    private String storeQueue;
    @Value("${spring.rabbitmq.exchanges.store-exchange}")
    private String storeExchange;

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
                        om.getTypeFactory().constructCollectionType(ArrayList.class, TrxDTO.class);
                        var trxDTO = om.convertValue(message.getMessage(), TrxDTO.class);
                        var answer = courierService.bookingCourier(trxDTO);
                        trxDTO.setDeliveryStatus(answer);
                        if (!(trxDTO.getDeliveryStatus().equals("Ok")
                                && trxDTO.getPayStatus().equals("Ok")
                                && trxDTO.getStoreStatus().equals("Ok"))) {
                            rt.convertAndSend(storeExchange, storeQueue,
                                    new RMessage(UUID.randomUUID().toString(), "cancelBookingFood", new CancelDTO(trxDTO.getOrder().getOrderId()))
                            );
                            rt.convertAndSend(payExchange, payQueue,
                                    new RMessage(UUID.randomUUID().toString(), "refund", new CancelDTO(trxDTO.getOrder().getOrderId()))
                            );
                        }
                        rt.convertAndSend(serviceAnswerExchange, serviceAnswerQueue,
                                new RMessage(UUID.randomUUID().toString(), "result", trxDTO)
                        );
                    }
/*                    case "cancelBookingCourier" ->  {
                        om.getTypeFactory().constructCollectionType(ArrayList.class, BookingCourierDTO.class);
                        var bookingCourierMsg = om.convertValue(message.getMessage(), BookingCourierDTO.class);
                        courierScheduleService.cancelBookingCourier(bookingCourierMsg);
                    }*/
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
