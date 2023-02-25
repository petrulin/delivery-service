package com.otus.deliveryservice.repository;

import com.otus.deliveryservice.entity.CourierSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CourierScheduleRepository extends JpaRepository<CourierSchedule, Long> {


    List<CourierSchedule> findAllByDeliveryDateAndDeliveryHour(LocalDate deliveryDate, Long deliveryHour);
    List<CourierSchedule>  findAllByOrderId(Long orderId);
}
