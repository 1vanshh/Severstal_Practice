package ru.severstal.backend.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.severstal.backend.entity.Client;
import ru.severstal.backend.entity.Order;
import ru.severstal.backend.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Утилитный класс для построения {@link Specification} объектов,
 * используемых при поиске и фильтрации заказов.
 *
 * <p>Класс формирует динамическую спецификацию для сущности {@link Order}
 * на основе переданных параметров фильтрации. Если параметр равен {@code null},
 * соответствующее условие не добавляется в итоговый запрос.</p>
 *
 * <p>Используется в репозиториях Spring Data JPA для выполнения поиска заказов
 * по статусу, данным клиента, диапазону суммы и диапазону даты создания.</p>
 */
final class OrderSpecificationBuilder {

    private OrderSpecificationBuilder(){
    }

    /**
     * Создает спецификацию для поиска заказов по переданным критериям.
     *
     * <p>Метод добавляет в спецификацию только те условия, для которых
     * соответствующие параметры не равны {@code null}.</p>
     *
     * <p>Фильтрация выполняется по следующим полям:</p>
     * <ul>
     *     <li>статус заказа;</li>
     *     <li>имя клиента с частичным совпадением без учета регистра;</li>
     *     <li>email клиента с полным совпадением без учета регистра;</li>
     *     <li>минимальная сумма заказа включительно;</li>
     *     <li>максимальная сумма заказа включительно;</li>
     *     <li>начало диапазона даты создания заказа включительно;</li>
     *     <li>конец диапазона даты создания заказа включительно.</li>
     * </ul>
     *
     * @param status статус заказа для фильтрации;
     * @param clientName имя клиента или часть имени клиента для поиска;
     *
     * @param clientEmail email клиента для поиска с полным совпадением;
     *
     * @param minAmount минимальная сумма заказа включительно;
     *
     * @param maxAmount максимальная сумма заказа включительно;
     *
     * @param dateFrom начало диапазона даты и времени создания заказа включительно;
     *
     * @param dateTo конец диапазона даты и времени создания заказа включительно;
     *
     * @return спецификация {@link Specification} для поиска заказов,
     *         соответствующих заданным критериям
     */
    public static Specification<Order> buildSearchSpecification(
            Status status,
            String clientName,
            String clientEmail,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            LocalDateTime dateFrom,
            LocalDateTime dateTo
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Order, Client> client = root.join("client");

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (clientName != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(client.get("name")),
                        "%" + clientName.toLowerCase(Locale.ROOT) + "%"
                ));
            }

            if (clientEmail != null) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(client.get("email")),
                        clientEmail.toLowerCase(Locale.ROOT)
                ));
            }

            if (minAmount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount));
            }

            if (maxAmount != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount));
            }

            if (dateFrom != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), dateFrom));
            }

            if (dateTo != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), dateTo));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}