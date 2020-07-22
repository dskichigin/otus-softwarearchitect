package dsk.otus.softwarearchitect.task10.warehouse.core;

import dsk.otus.softwarearchitect.task10.warehouse.entity.OperationEntity;
import dsk.otus.softwarearchitect.task10.warehouse.object.TypeOperation;
import dsk.otus.softwarearchitect.task10.warehouse.entity.WarehouseEntity;
import dsk.otus.softwarearchitect.task10.warehouse.repository.WatehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WarehouseCore {
    @Autowired
    WatehouseRepository watehouseRepository;

    public WarehouseEntity createArrival(OperationEntity operation) throws Exception {
        if (operation.getCol() <= 0) throw new Exception("Количество не может быть меньше или равно 0");

        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(java.util.UUID.randomUUID().toString());
        warehouse.setOrderId(operation.getOrderId());
        warehouse.setProductId(operation.getProductId());
        warehouse.setCol(operation.getCol());
        warehouse.setType(TypeOperation.ARRIVAL.name());
        warehouse.setActive(1);
        warehouse.setNew(true);

        watehouseRepository.save(warehouse);
        return warehouse;
    }

    public WarehouseEntity createReserve(OperationEntity operation) throws Exception {
        Long remains = getRemains(operation.getProductId());
        if (remains < operation.getCol()) throw new Exception("На складе не достаточно товара");

        WarehouseEntity warehouse = new WarehouseEntity();
        warehouse.setId(java.util.UUID.randomUUID().toString());
        warehouse.setOrderId(operation.getOrderId());
        warehouse.setProductId(operation.getProductId());
        warehouse.setCol(operation.getCol());
        warehouse.setType(TypeOperation.RESERVE.name());
        warehouse.setActive(1);
        warehouse.setNew(true);

        watehouseRepository.save(warehouse);
        return warehouse;
    }

    public void cancelOperationByOrderId(String orderId) {
        watehouseRepository.cancelOperationByOrderId(orderId);
    }

    public Long getRemains(String productId) {
        return watehouseRepository.getRemains(productId);
    }
}
