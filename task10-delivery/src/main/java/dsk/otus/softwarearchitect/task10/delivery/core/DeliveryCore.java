package dsk.otus.softwarearchitect.task10.delivery.core;

import dsk.otus.softwarearchitect.task10.delivery.entity.SlotEntity;
import dsk.otus.softwarearchitect.task10.delivery.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@Component
public class DeliveryCore {
    @Autowired
    SlotRepository slotRepository;

    public void initSlots() {
        slotRepository.deleteAll();
        for (int f=10; f<20; f++) {
            SlotEntity slot = new SlotEntity();
            slot.setId(java.util.UUID.randomUUID().toString());
            slot.setTimeFrom(String.valueOf(f)+":00");
            slot.setTimeTo(String.valueOf(f+1)+":00");
            slot.setNew(true);
            slotRepository.save(slot);
        }
    }
    public SlotEntity getSlot(String id) {
        Optional optional = slotRepository.findById(id);
        if (!optional.isPresent()) return null;
        return (SlotEntity) optional.get();
    }
    public SlotEntity setDeliverySlot(String orderId, String timeFrom, String timeTo) throws Exception {
        SlotEntity slot = slotRepository.findSlotByTime(timeFrom, timeTo);
        if (slot == null) throw new Exception("Временной слот не найден");
        if (slot.getOrderId() != null)
            if (slot.getOrderId().equals("")) throw new Exception("Временной слот не найден");

        slot.setNew(false);
        slot.setOrderId(orderId);
        slotRepository.save(slot);
        return slot;
    }
    public void cancelDelivery(String orderId) {
        slotRepository.cancelSlotByOrderId(orderId);
    }
}
