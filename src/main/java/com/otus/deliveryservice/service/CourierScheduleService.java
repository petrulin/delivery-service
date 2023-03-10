package com.otus.deliveryservice.service;

import com.otus.deliveryservice.entity.CourierSchedule;

import java.time.LocalDate;
import java.util.List;


public interface CourierScheduleService {

    CourierSchedule save(CourierSchedule user);

    List<CourierSchedule> findAllbyDeliveryDateanddeliveryHour(LocalDate deliveryDate, Long deliveryHour);

/*    void cancelBookingCourier(BookingCourierDTO bookingCourier);*/
}
