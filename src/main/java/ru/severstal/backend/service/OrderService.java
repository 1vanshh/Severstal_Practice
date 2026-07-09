package ru.severstal.backend.service;

import ru.severstal.backend.dto.request.OrderRequest;
import ru.severstal.backend.dto.response.OrderResponse;
import ru.severstal.backend.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends CrudService<OrderResponse, OrderRequest>{

    List<OrderResponse> getByClientId(Long clientId);

    List<OrderResponse> getByStatus(Status status);

    List<OrderResponse> searchOrders(
            Status status,
            String clientName,
            String clientEmail,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            LocalDateTime dateFrom,
            LocalDateTime dateTo
    );
}
