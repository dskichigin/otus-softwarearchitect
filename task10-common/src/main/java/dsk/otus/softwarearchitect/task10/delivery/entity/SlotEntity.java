package dsk.otus.softwarearchitect.task10.delivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@JsonIgnoreProperties(value = { "new" })
@Table("slots")
public class SlotEntity implements Persistable<String> {

    @Id
    @Column("id")
    private String id;
    @Column("orderId")
    private String orderId;
    @Column("time_from")
    private String timeFrom;
    @Column("time_to")
    private String timeTo;

    @Transient
    private boolean newEntity = true;

    @Override
    public String getId() { return id; }

    public void setId(String id) { this.id = id;  }

    public String getOrderId() { return orderId; }

    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getTimeFrom() { return timeFrom; }

    public void setTimeFrom(String timeFrom) { this.timeFrom = timeFrom; }

    public String getTimeTo() { return timeTo; }

    public void setTimeTo(String timeTo) { this.timeTo = timeTo; }

    @Override
    public boolean isNew() {
        return newEntity;
    }

    public void setNew(boolean newEntity) {
        this.newEntity = newEntity;
    }
}
