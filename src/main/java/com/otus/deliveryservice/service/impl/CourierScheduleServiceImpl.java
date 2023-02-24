package com.otus.deliveryservice.service.impl;

import com.otus.deliveryservice.entity.CourierSchedule;
import com.otus.deliveryservice.repository.CourierScheduleRepository;
import com.otus.deliveryservice.service.CourierScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("courierScheduleService")
@RequiredArgsConstructor
public class CourierScheduleServiceImpl implements CourierScheduleService {
    private final CourierScheduleRepository courierScheduleRepository;

    @Override
    public CourierSchedule save(CourierSchedule order) {
        return courierScheduleRepository.save(order);
    }

    @Override
    public List<CourierSchedule> findAllbyDeliveryDateanddeliveryHour(LocalDate deliveryDate, Long deliveryHour) {
        return courierScheduleRepository.findAllByDeliveryDateAndDeliveryHour(deliveryDate, deliveryHour);
    }
}
