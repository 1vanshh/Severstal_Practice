package ru.severstal.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.severstal.backend.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private String description;

    private BigDecimal amount;

    private Status status;

    private LocalDateTime createdDate;

    private String clientName;
}
