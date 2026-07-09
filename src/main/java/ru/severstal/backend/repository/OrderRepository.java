package ru.severstal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.severstal.backend.entity.Order;
import ru.severstal.backend.enums.Status;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByClientId(Long clientId);

    List<Order> findByStatus(Status status);
}
