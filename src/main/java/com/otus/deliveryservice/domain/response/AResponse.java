package com.otus.deliveryservice.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otus.deliveryservice.error.ApplicationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class AResponse {

    private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";

    private int errorCode;

    private String errorMessage;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime operationTime;

    protected AResponse() {
        this(ApplicationError.SUCCESS);
    }

    protected AResponse(ApplicationError applicationError) {
        this(applicationError.getErrorCode(), applicationError.getMessage());
    }

    protected AResponse(Integer errorCode, String errorMessage) {
        this(errorCode, errorMessage, LocalDateTime.now());
    }
}
