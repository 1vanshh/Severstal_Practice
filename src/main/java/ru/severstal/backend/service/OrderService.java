package ru.severstal.backend.service;

import ru.severstal.backend.dto.request.OrderRequest;
import ru.severstal.backend.dto.response.OrderResponse;
import ru.severstal.backend.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService extends CrudService<OrderResponse, OrderRequest>{

    /**
     * Возвращает сущность по уникальному идентификатору его клиента.
     *
     * @param clientId уникальный идентификатор клиента заказа
     * @return список {@link OrderResponse}, соответствующий указанному идентификатору
     */
    List<OrderResponse> getByClientId(Long clientId);

    /**
     * Возвращает сущность по статусу заказа.
     *
     * @param status {@link Status} статус заказа
     * @return список {@link OrderResponse}, соответствующий указанному статусу
     */
    List<OrderResponse> getByStatus(Status status);


    /**
     * Выполняет поиск заказов по переданным критериям фильтрации.
     *
     * <p>Все параметры являются необязательными. Если параметр равен {@code null},
     * соответствующий фильтр не применяется. Метод возвращает все заказы,
     * которые соответствуют указанным условиям.</p>
     *
     * @param status статус заказа для фильтрации;
     * @param clientName имя клиента или часть имени клиента для поиска;
     * @param clientEmail email клиента для поиска с полным совпадением;
     * @param minAmount минимальная сумма заказа включительно;
     * @param maxAmount максимальная сумма заказа включительно;
     * @param dateFrom начало диапазона даты и времени заказа включительно;
     * @param dateTo конец диапазона даты и времени заказа включительно;
     * @return список объектов {@link OrderResponse}, соответствующих заданным фильтрам;
     *         возвращает пустой список, если заказы не найдены
     */
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
