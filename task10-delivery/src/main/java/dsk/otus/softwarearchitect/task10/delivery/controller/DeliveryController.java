package dsk.otus.softwarearchitect.task10.delivery.controller;

import dsk.otus.softwarearchitect.task10.delivery.core.DeliveryCore;
import dsk.otus.softwarearchitect.task10.delivery.entity.SlotEntity;
import dsk.otus.softwarearchitect.task10.delivery.entity.VersionEntity;
import dsk.otus.softwarearchitect.task10.delivery.repository.SlotRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
public class DeliveryController {
    @Autowired
    SlotRepository slotRepository;

    @Autowired
    DeliveryCore deliveryCore;

    public DeliveryController() { }

    /**
     * инициализируем временные слоты
     * @return
     * @throws Exception
     */
    @PostMapping(path = "/delivery/slots/init", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity initSlots() throws Exception {
        deliveryCore.initSlots();
        return ResponseEntity.ok().build();
    }


    @GetMapping(path = "/delivery/slots/{id}", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<SlotEntity> getSlot(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(deliveryCore.getSlot(id));
    }

    @GetMapping(value = "/delivery/slots", produces = "application/json")
    @Counted
    public ResponseEntity<List<SlotEntity>> getSlotByOrder(@RequestParam("order_id") String id, HttpServletRequest request) {
        return ResponseEntity.ok(slotRepository.findByOrderId(id));
    }

    /**
     * получить все слоты
     * @param request
     * @return
     */
    @GetMapping(value = "/delivery/slots/all", produces = "application/json")
    @Counted
    public ResponseEntity<List<SlotEntity>> getSlots(HttpServletRequest request) {
        return ResponseEntity.ok(slotRepository.findAll());
    }

    /**
     * получить доступные слоты
     * @param request
     * @return
     */
    @GetMapping(value = "/delivery/slots/available", produces = "application/json")
    @Counted
    public ResponseEntity<List<SlotEntity>> getSlotsAvailable(HttpServletRequest request) {
        return ResponseEntity.ok(slotRepository.findAvailable());
    }

    /**
     * назначить доставку заказа во временной слот
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(path="/delivery", produces = "application/json")
    @Counted
    public @ResponseBody ResponseEntity<SlotEntity> setDeliverySlot(@RequestParam("order_id") String id,
                                                        @RequestParam("time_from") String timeFrom,
                                                        @RequestParam("time_to") String timeTo,
                                                        HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(deliveryCore.setDeliverySlot(id, timeFrom, timeTo));
    }
    /**
     * отмена операции
     * @param orderId
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping(path="/delivery/cancel", produces = "application/json")
    @Counted
    public @ResponseBody ResponseEntity cancelDelivery(@RequestParam("order_id") String orderId, HttpServletRequest request) throws Exception {
        deliveryCore.cancelDelivery(orderId);
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
