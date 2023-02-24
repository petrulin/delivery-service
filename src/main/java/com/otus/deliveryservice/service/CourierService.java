package com.otus.deliveryservice.service;

import com.otus.deliveryservice.rabbitmq.domain.dto.BookingCourierDTO;
import com.otus.deliveryservice.rabbitmq.domain.dto.BookingCourierResponse;


public interface CourierService {

    BookingCourierResponse bookingCourier(BookingCourierDTO bookingCourier);
}
