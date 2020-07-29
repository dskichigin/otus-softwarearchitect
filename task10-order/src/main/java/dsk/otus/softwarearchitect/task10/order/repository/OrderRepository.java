package dsk.otus.softwarearchitect.task10.order.repository;

import dsk.otus.softwarearchitect.task10.order.entity.OrderEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, String> {
    @Query("select count(*) from orders")
    Long getCountOrders();
}