package ru.severstal.backend.dto.response;

import lombok.Builder;
import ru.severstal.backend.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OrderResponse (

    Long id,

    String description,

    BigDecimal amount,

    Status status,

    LocalDateTime createdDate,

    String clientName
) {}
