package ru.severstal.backend.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.severstal.backend.enums.Status;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Order description is required")
    @Size(max = 500, message = "Order description must be less than or equal to 500 characters")
    private String description;

    @NotNull(message = "Order amount is required")
    @DecimalMin(value = "0.01", message = "Order amount must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Order amount must have up to 8 integer digits and 2 fraction digits")
    private BigDecimal amount;

    @NotNull(message = "Order status is required")
    private Status status;

    @NotNull(message = "Client id is required")
    private Long clientId;
}