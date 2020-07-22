package dsk.otus.softwarearchitect.task10.order.repository;

import dsk.otus.softwarearchitect.task10.order.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, String> {
}