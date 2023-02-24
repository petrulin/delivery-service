package com.otus.deliveryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "courier_schedule", schema = "delivery_service", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourierSchedule {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "courier_id", nullable = false)
    private Long courierId;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @Column(name = "delivery_hour", nullable = false)
    private Long deliveryHour;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    public CourierSchedule(Long courierId, LocalDate deliveryDate, Long deliveryHour, Long orderId) {
        this.courierId = courierId;
        this.deliveryDate = deliveryDate;
        this.deliveryHour = deliveryHour;
        this.orderId = orderId;
    }
}
