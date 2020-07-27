package dsk.otus.softwarearchitect.task10.order.controller;

import dsk.otus.softwarearchitect.task10.order.core.OrderCore;
import dsk.otus.softwarearchitect.task10.order.entity.OrderEntity;
import dsk.otus.softwarearchitect.task10.order.entity.VersionEntity;
import dsk.otus.softwarearchitect.task10.order.repository.OrderRepository;
import dsk.otus.softwarearchitect.task10.payment.entity.PaymentEntity;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderCore orderCore;

    public OrderController() { }

    @PostMapping(path = "/orders", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<OrderEntity> createOrder(@RequestBody OrderEntity order) throws Exception {
        return ResponseEntity.ok(orderCore.createOrder(order));
    }

    @GetMapping(path = "/version", produces = "application/json")
    @Counted
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("1.0");
        return version;
    }
}
