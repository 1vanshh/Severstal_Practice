package ru.severstal.backend.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.severstal.backend.entity.Order;
import ru.severstal.backend.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static ru.severstal.backend.repository.OrderSpecificationBuilder.buildSearchSpecification;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByClientId(Long clientId);

    List<Order> findByStatus(Status status);

    default List<Order> findAllByPredicate(
            Status status,
            String clientName,
            String clientEmail,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            LocalDateTime dateFrom,
            LocalDateTime dateTo,
            Sort sort
    ) {
        return this.findAll(buildSearchSpecification(status,
                        clientName,
                        clientEmail,
                        minAmount,
                        maxAmount,
                        dateFrom,
                        dateTo),
                        sort);
    }
}
