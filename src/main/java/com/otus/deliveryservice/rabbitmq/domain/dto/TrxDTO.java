
package com.otus.deliveryservice.rabbitmq.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TrxDTO {
    private OrderDTO order;
    private String payStatus;
    private String storeStatus;
    private String deliveryStatus;
}
