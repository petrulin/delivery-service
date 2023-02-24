package com.otus.deliveryservice.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationError {

    SUCCESS("", 0),
    INTERNAL_ERROR("Internal error", -9000);

    private final String message;
    private final int errorCode;
}
