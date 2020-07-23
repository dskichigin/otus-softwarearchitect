package dsk.otus.softwarearchitect.task10.warehouse.repository;

import dsk.otus.softwarearchitect.task10.warehouse.entity.WarehouseEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface WatehouseRepository extends CrudRepository<WarehouseEntity, String> {
    @Query("select sum((case when type='ARRIVAL' then 1 when type='RESERVE' then -1 else 1 end)*col) " +
            "from warehouses where productId = :productId and active = 1")
    Long getRemains(@Param("productId") String productId);


    @Query("update warehouses set active = 0 where orderId = :orderId")
    @Modifying
    void cancelOperationByOrderId(@Param("orderId") String orderId);

}