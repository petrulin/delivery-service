
package com.otus.deliveryservice.rabbitmq.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private BigDecimal amount;
    private BigDecimal discount;
    private Long quantity;
    private Long orderId;
    private String userName;
    private List<DishDTO> dishs;
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDate deliveryDate;
    private Long deliveryHour;

}
