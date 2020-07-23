package dsk.otus.softwarearchitect.task10.warehouse.controller;

import dsk.otus.softwarearchitect.task10.warehouse.core.WarehouseCore;
import dsk.otus.softwarearchitect.task10.warehouse.entity.OperationEntity;
import dsk.otus.softwarearchitect.task10.warehouse.entity.RemainEntity;
import dsk.otus.softwarearchitect.task10.warehouse.entity.VersionEntity;
import dsk.otus.softwarearchitect.task10.warehouse.entity.WarehouseEntity;
import dsk.otus.softwarearchitect.task10.warehouse.repository.WatehouseRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@RestController
@Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
public class WarehouseController {
    @Autowired
    WatehouseRepository watehouseRepository;

    @Autowired
    WarehouseCore warehouseCore;

    public WarehouseController() { }

    @PostMapping(path = "/warehouses/arrival", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<WarehouseEntity> createArrival(@RequestBody OperationEntity operation) throws Exception {
        return ResponseEntity.ok(warehouseCore.createArrival(operation));
    }
    @PostMapping(path = "/warehouses/reserve", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<WarehouseEntity> createReserve(@RequestBody OperationEntity operation) throws Exception {
        return ResponseEntity.ok(warehouseCore.createReserve(operation));
    }

    @GetMapping(path = "/warehouses/remains/{id}", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<RemainEntity> getProduct(@PathVariable("id") String productId, HttpServletRequest request) {
        return ResponseEntity.ok(new RemainEntity(productId, warehouseCore.getRemains(productId)));
    }

    @PutMapping(path="/warehouses/cancel", produces = "application/json")
    @Counted
    public @ResponseBody ResponseEntity cancelOperation(@RequestParam("order_id") String orderId, HttpServletRequest request) {
        warehouseCore.cancelOperationByOrderId(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/version", produces = "application/json")
    @Counted
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("1.0");
        return version;
    }
}
