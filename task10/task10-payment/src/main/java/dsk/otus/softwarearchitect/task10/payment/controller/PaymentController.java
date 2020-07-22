package dsk.otus.softwarearchitect.task10.payment.controller;

import dsk.otus.softwarearchitect.task10.payment.core.PaymentCore;
import dsk.otus.softwarearchitect.task10.payment.entity.PaymentEntity;
import dsk.otus.softwarearchitect.task10.payment.entity.VersionEntity;
import dsk.otus.softwarearchitect.task10.payment.repository.PaymentRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
public class PaymentController {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentCore paymentCore;

    public PaymentController() { }

    @PostMapping(path = "/payments", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<PaymentEntity> createPayment(@RequestBody PaymentEntity payment) throws Exception {
        return ResponseEntity.ok(paymentCore.createPayment(payment));
    }

    @GetMapping(path = "/payments/{id}", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<PaymentEntity> getPayment(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(paymentCore.findById(id));
    }

    @GetMapping(value = "/payments", produces = "application/json")
    @Counted
    public ResponseEntity<List<PaymentEntity>> getPaymentsByOrder(@PathParam("orderid") String id, HttpServletRequest request) {
        return ResponseEntity.ok(paymentRepository.findByOrderId(id));
    }

    /**
     * отмена операции
     * @param orderId
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping(path="/payments/cancel", consumes = "application/json", produces = "application/json")
    @Counted
    public @ResponseBody ResponseEntity<PaymentEntity> cancelProduct(@PathParam("order_id") String orderId, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(paymentCore.cancelPayment(orderId));
    }

    @GetMapping(path = "/version", produces = "application/json")
    @Counted
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("1.0");
        return version;
    }
}
