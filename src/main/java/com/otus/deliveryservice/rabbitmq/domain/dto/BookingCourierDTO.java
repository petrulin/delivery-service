
package com.otus.deliveryservice.rabbitmq.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookingCourierDTO {
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate deliveryDate;
    private Long deliveryHour;

    private Long orderId;


}
