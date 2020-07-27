package dsk.otus.softwarearchitect.task10.payment.core;

import dsk.otus.softwarearchitect.task10.payment.entity.PaymentEntity;
import dsk.otus.softwarearchitect.task10.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sun.security.krb5.internal.PAData;

import java.util.Optional;

@Component
public class PaymentCore {
    @Autowired
    PaymentRepository paymentRepository;

    public PaymentEntity createPayment(PaymentEntity payment) throws Exception {
        if (payment.getSumma() < 0) throw new Exception("Отрицательный платеж недопустим.");

        payment.setNew(true);
        payment.setStatus("оплачен");
        paymentRepository.save(payment);
        return payment;
    }
    public PaymentEntity cancelPayment(String id) throws Exception {
        PaymentEntity payment = findById(id);
        payment.setStatus("отменен");
        payment.setNew(false);
        paymentRepository.save(payment);
        return payment;
    }
    public void cancelPaymentByOrderId(String orderId) {
        paymentRepository.cancelOperationByOrderId(orderId);
    }
    public PaymentEntity findById(String id) throws Exception {
        Optional optPayment = paymentRepository.findById(id);
        if (!optPayment.isPresent()) throw new Exception("Не найден платеж по идентификатору.");

        return (PaymentEntity)optPayment.get();
    }
}
