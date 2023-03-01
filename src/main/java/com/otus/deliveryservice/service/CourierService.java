package com.otus.deliveryservice.service;

import com.otus.deliveryservice.rabbitmq.domain.dto.TrxDTO;


public interface CourierService {

    String bookingCourier(TrxDTO trxDTO);
}
