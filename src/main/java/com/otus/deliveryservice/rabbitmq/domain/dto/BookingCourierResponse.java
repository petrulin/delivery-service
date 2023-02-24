
package com.otus.deliveryservice.rabbitmq.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BookingCourierResponse {
    private String fio;
    private String phone;
    private String status;

    public BookingCourierResponse(String status) {
        this.status = status;
    }
}
