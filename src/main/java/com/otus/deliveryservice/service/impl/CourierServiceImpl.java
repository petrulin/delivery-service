package com.otus.deliveryservice.service.impl;

import com.otus.deliveryservice.entity.Courier;
import com.otus.deliveryservice.entity.CourierSchedule;
import com.otus.deliveryservice.rabbitmq.domain.dto.TrxDTO;
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
    public String bookingCourier(TrxDTO trxDTO) {
        try {
            List<Long> bookedList =  courierScheduleService.findAllbyDeliveryDateanddeliveryHour(
                        trxDTO.getOrder().getDeliveryDate(), trxDTO.getOrder().getDeliveryHour()
                    ).stream().map(CourierSchedule::getCourierId).toList();
            List<Courier> couriers = bookedList.isEmpty() ? courierRepository.findAll() : courierRepository.findAllByIdNotIn(bookedList);
            if (couriers.isEmpty()) {
                return "All couriers are busy";
            }
            Random rand = new Random();
            var selectedCourier = couriers.get(rand.nextInt(couriers.size()));
            courierScheduleService.save(
                    new CourierSchedule(
                            selectedCourier.getId(),
                            trxDTO.getOrder().getDeliveryDate(),
                            trxDTO.getOrder().getDeliveryHour(),
                            trxDTO.getOrder().getOrderId())
            );
            return "Ok";
        } catch (Exception e) {
            return "Error";
        }
    }

}
