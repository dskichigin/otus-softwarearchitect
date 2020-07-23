package dsk.otus.softwarearchitect.task10.payment.repository;

import dsk.otus.softwarearchitect.task10.payment.entity.PaymentEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends CrudRepository<PaymentEntity, String> {
    @Query("select * from payments where orderId = :orderId")
    List<PaymentEntity> findByOrderId(@Param("orderId") String orderId);

}