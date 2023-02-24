package com.otus.deliveryservice.rabbitmq.domain.dto;

import java.time.format.DateTimeFormatter;

public class DateFormat {
    public static String DF_PATTERN = "dd.MM.yyyy";
    public static final DateTimeFormatter DF = DateTimeFormatter.ofPattern(DF_PATTERN);
}
