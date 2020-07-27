package dsk.otus.softwarearchitect.task10.payment.controller;

import dsk.otus.softwarearchitect.task10.payment.core.PaymentCore;
import dsk.otus.softwarearchitect.task10.payment.entity.PaymentEntity;
import dsk.otus.softwarearchitect.task10.payment.entity.VersionEntity;
import dsk.otus.softwarearchitect.task10.payment.repository.PaymentRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ResponseEntity<List<PaymentEntity>> getPaymentsByOrder(@RequestParam("order_id") String id, HttpServletRequest request) {
        return ResponseEntity.ok(paymentRepository.findByOrderId(id));
    }

    /**
     * отмена операции
     * @param orderId
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping(path="/payments/cancel", produces = "application/json")
    @Counted
    public @ResponseBody ResponseEntity cancelProduct(@RequestParam("order_id") String orderId, HttpServletRequest request) throws Exception {
        paymentCore.cancelPaymentByOrderId(orderId);
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
