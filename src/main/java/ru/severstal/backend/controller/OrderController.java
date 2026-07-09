package ru.severstal.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.severstal.backend.dto.request.OrderRequest;
import ru.severstal.backend.dto.response.OrderResponse;
import ru.severstal.backend.enums.Status;
import ru.severstal.backend.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody @Valid OrderRequest request) {
        return orderService.create(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse updateOrder(
            @PathVariable Long id,
            @RequestBody @Valid OrderRequest request
    ) {
        return orderService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
    }

    @GetMapping("/client/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrdersByClientId(@PathVariable Long clientId) {
        return orderService.getByClientId(clientId);
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrdersByStatus(@PathVariable Status status) {
        return orderService.getByStatus(status);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> searchOrders(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String clientName,
            @RequestParam(required = false) String clientEmail,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) LocalDateTime dateFrom,
            @RequestParam(required = false) LocalDateTime dateTo
    ) {
        return orderService.searchOrders(
                status,
                clientName,
                clientEmail,
                minAmount,
                maxAmount,
                dateFrom,
                dateTo
        );
    }
}