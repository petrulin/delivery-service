package com.otus.deliveryservice.service.impl;

import com.otus.deliveryservice.entity.Courier;
import com.otus.deliveryservice.entity.CourierSchedule;
import com.otus.deliveryservice.rabbitmq.domain.dto.BookingCourierDTO;
import com.otus.deliveryservice.rabbitmq.domain.dto.BookingCourierResponse;
import com.otus.deliveryservice.repository.CourierRepository;
import com.otus.deliveryservice.service.CourierScheduleService;
import com.otus.deliveryservice.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service("courierService")
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {
    private final CourierRepository courierRepository;

    private final CourierScheduleService courierScheduleService;

    @Override
    public BookingCourierResponse bookingCourier(BookingCourierDTO bookingCourier) {
        try {
            List<Long> bookedList =  courierScheduleService.findAllbyDeliveryDateanddeliveryHour(bookingCourier.getDeliveryDate(), bookingCourier.getDeliveryHour())
                    .stream().map(CourierSchedule::getCourierId).toList();
            List<Courier> couriers = bookedList.isEmpty() ? courierRepository.findAll() : courierRepository.findAllByIdNotIn(bookedList);
            if (couriers.isEmpty()) {
                return new BookingCourierResponse("All couriers are busy");
            }
            Random rand = new Random();
            var selectedCourier = couriers.get(rand.nextInt(couriers.size()));
            courierScheduleService.save(new CourierSchedule(selectedCourier.getId(), bookingCourier.getDeliveryDate(), bookingCourier.getDeliveryHour(), bookingCourier.getOrderId()));
            return new BookingCourierResponse(selectedCourier.getFio(), selectedCourier.getPhone(), "Ok");
        } catch (Exception e) {
            return new BookingCourierResponse("Error");
        }
    }

}
