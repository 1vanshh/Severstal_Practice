package ru.severstal.backend.service.impl;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.severstal.backend.dto.request.OrderRequest;
import ru.severstal.backend.dto.response.OrderResponse;
import ru.severstal.backend.entity.Client;
import ru.severstal.backend.entity.Order;
import ru.severstal.backend.enums.Status;
import ru.severstal.backend.exception.BadRequestException;
import ru.severstal.backend.exception.NotFoundException;
import ru.severstal.backend.repository.ClientRepository;
import ru.severstal.backend.repository.OrderRepository;
import ru.severstal.backend.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    @Override
    public OrderResponse create(OrderRequest request) {
        Client client = getClientForOrderRequest(request.clientId());

        Order order = new Order();
        order.setDescription(request.description());
        order.setAmount(request.amount());
        order.setStatus(request.status());
        order.setClient(client);

        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getById(Long id) {
        Order order = getOrderEntityById(id);
        return mapToResponse(order);
    }

    @Override
    public OrderResponse update(Long id, OrderRequest request) {
        Order order = getOrderEntityById(id);
        Client client = getClientForOrderRequest(request.clientId());

        order.setDescription(request.description());
        order.setAmount(request.amount());
        order.setStatus(request.status());
        order.setClient(client);

        Order updatedOrder = orderRepository.save(order);

        return mapToResponse(updatedOrder);
    }

    @Override
    public void delete(Long id) {
        Order order = getOrderEntityById(id);
        orderRepository.delete(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getByClientId(Long clientId) {
        Client client = getClientEntityById(clientId);

        return orderRepository.findByClientId(client.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getByStatus(Status status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> searchOrders(
            Status status,
            String clientName,
            String clientEmail,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            LocalDateTime dateFrom,
            LocalDateTime dateTo
    ) {
        return orderRepository.findAllByPredicate(
                        status,
                        normalize(clientName),
                        normalize(clientEmail),
                        minAmount,
                        maxAmount,
                        dateFrom,
                        dateTo,
                        Sort.by(Sort.Direction.DESC, "createdDate"))
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Order getOrderEntityById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
    }

    private Client getClientEntityById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with id: " + id));
    }

    private Client getClientForOrderRequest(Long clientId) {
        if (clientId == null) {
            throw new BadRequestException("Client id is required");
        }

        return clientRepository.findById(clientId)
                .orElseThrow(() -> new BadRequestException("Client not found with id: " + clientId));
    }

    private String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .description(order.getDescription())
                .amount(order.getAmount())
                .status(order.getStatus())
                .createdDate(order.getCreatedDate())
                .clientName(order.getClient().getName())
                .build();
    }
}

