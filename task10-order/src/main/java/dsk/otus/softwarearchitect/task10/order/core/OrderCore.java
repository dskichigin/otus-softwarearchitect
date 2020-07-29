package dsk.otus.softwarearchitect.task10.order.core;

import dsk.otus.softwarearchitect.task10.delivery.entity.SlotEntity;
import dsk.otus.softwarearchitect.task10.order.entity.OrderEntity;
import dsk.otus.softwarearchitect.task10.order.object.StatusOrder;
import dsk.otus.softwarearchitect.task10.order.repository.OrderRepository;
import dsk.otus.softwarearchitect.task10.payment.entity.PaymentEntity;
import dsk.otus.softwarearchitect.task10.warehouse.entity.OperationEntity;
import dsk.otus.softwarearchitect.task10.warehouse.entity.WarehouseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
public class OrderCore {
    @Autowired
    OrderRepository orderRepository;

    String server_payment="paymentservice:9000";
    String server_warehouse="warehouseservice:9000";
    String server_delivery="deliveryservice:9000";

    RestTemplate restTemplate = new RestTemplate();

    public OrderEntity createOrder(OrderEntity order) {
        // проверяем наличие ордела с указанным GUID в случаии повторных запросов
        Optional<OrderEntity> opt = orderRepository.findById(order.getId());
        if (opt.isPresent()) return opt.get();

        order.setNew(true);
        order.setStatus(StatusOrder.IN_WORK.name());
        orderRepository.save(order);

        try {
            PaymentEntity payment = null;
            try {
                createPayment(order);
            } catch (Exception e) {
                throw e;
            }

            // резервируем товар на складе
            WarehouseEntity warehouse = null;
            try {
                createWarehouseReserve(order);
            } catch (Exception e) {
                cancelPayment(order);
                throw e;
            }

            // регистрируем доставку
            try {
                createDelivery(order);
            } catch (Exception e) {
                cancelWarehouse(order);
                cancelPayment(order);
                throw e;
            }
            order.setNew(false);
            order.setStatus(StatusOrder.COMPLETE.name());
            orderRepository.save(order);

        } catch (Exception e) {
            order.setNew(false);
            order.setStatus(StatusOrder.CANCELED.name());
            orderRepository.save(order);
            throw e;
        }
        return order;
    }

    private PaymentEntity createPayment(OrderEntity order) {
        // оформляем платеж
        PaymentEntity payment = new PaymentEntity();
        payment.setId(java.util.UUID.randomUUID().toString());
        payment.setOrderId(order.getId());
        payment.setSumma(order.getSumma());

        try {
            String url = "http://"+server_payment+"/payments";

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<PaymentEntity> request = new HttpEntity<>(payment, headers);

            ResponseEntity<PaymentEntity> data = restTemplate.exchange(url, HttpMethod.POST, request, PaymentEntity.class);
            return data.getBody();
        } catch (Exception e) {
            throw e;
        }
    }
    private void cancelPayment(OrderEntity order) {
        try {
            String url = "http://"+server_payment+"/payments/cancel";
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("order_id", order.getId());

            HttpHeaders headers = new HttpHeaders();
            HttpEntity request = new HttpEntity(headers);

            HttpEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.PUT,
                    request,
                    String.class);

//            ResponseEntity data = restTemplate.exchange(url, HttpMethod.PUT, request, (Class<Object>) null);
        } catch (Exception e) {
            throw e;
        }
    }
    private WarehouseEntity createWarehouseReserve(OrderEntity order) {
        // оформляем резерв
        OperationEntity operation = new OperationEntity();
        operation.setOrderId(order.getId());
        operation.setProductId(order.getProductId());
        operation.setCol(order.getCol());

        try {
            String url = "http://"+server_warehouse+"/warehouses/reserve";

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<OperationEntity> request = new HttpEntity<>(operation, headers);

            ResponseEntity<WarehouseEntity> data = restTemplate.exchange(url, HttpMethod.POST, request, WarehouseEntity.class);
            return data.getBody();
        } catch (Exception e) {
            throw e;
        }
    }
    private void cancelWarehouse(OrderEntity order) {
        try {
            String url = "http://"+server_warehouse+"/warehouses/cancel";

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("order_id", order.getId());

            HttpHeaders headers = new HttpHeaders();
            HttpEntity request = new HttpEntity(headers);

            restTemplate.put(builder.toUriString(), request);
//            ResponseEntity data = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, request, (Class<Object>) null);
        } catch (Exception e) {
            throw e;
        }
    }

    private void createDelivery(OrderEntity order) {

        try {
            String url = "http://"+server_delivery+"/delivery";

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("order_id", order.getId())
                    .queryParam("time_from", order.getDeliveryTimeFrom())
                    .queryParam("time_to", order.getDeliveryTimeTo());

            HttpHeaders headers = new HttpHeaders();
            HttpEntity request = new HttpEntity(headers);

//            restTemplate.put(builder.toUriString(), request);
            ResponseEntity<SlotEntity> data = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, request, SlotEntity.class);
        } catch (Exception e) {
            throw e;
        }
    }
    private void cancelDelivery(OrderEntity order) {
        try {
            String url = "http://"+server_payment+"/products/cancel";

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("order_id", order.getId());

            HttpHeaders headers = new HttpHeaders();
            HttpEntity request = new HttpEntity(null, headers);

            ResponseEntity data = restTemplate.exchange(builder.toString(), HttpMethod.PUT, request, (Class<Object>) null);
        } catch (Exception e) {
            throw e;
        }
    }

}
