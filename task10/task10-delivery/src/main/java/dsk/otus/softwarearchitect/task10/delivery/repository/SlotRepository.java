package dsk.otus.softwarearchitect.task10.delivery.repository;

import dsk.otus.softwarearchitect.task10.delivery.entity.SlotEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SlotRepository extends CrudRepository<SlotEntity, String> {
    @Query("select * from slots where orderId = :orderId")
    List<SlotEntity> findByOrderId(@Param("orderId") String orderId);

    @Query("select * from slots where orderId is null or orderId = '' order by time_from")
    List<SlotEntity> findAvailable();

    @Query("select * from slots order by time_from")
    List<SlotEntity> findAll();

    @Query("select * from slots where time_from = :time_from and time_to = :time_to limit 1")
    SlotEntity findSlotByTime(@Param("time_from") String timeFrom, @Param("time_to") String timeTo);

    @Query("update slots set orderId = null where orderId = :orderId")
    @Modifying
    void cancelSlotByOrderId(@Param("orderId") String orderId);

}