package dsk.otus.softwarearchitect.task10.payment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@JsonIgnoreProperties(value = { "new" })
@Table("payments")
public class PaymentEntity implements Persistable<String> {

    @Id
    @Column("id")
    private String id;
    @Column("orderId")
    private String orderId;
    @Column("summa")
    private Double summa;
    @Column("status")
    private String status;

    @Transient
    private boolean newEntity = true;

    @Override
    public String getId() { return id; }

    public void setId(String id) { this.id = id;  }

    public String getOrderId() { return orderId; }

    public void setOrderId(String orderId) { this.orderId = orderId; }

    public Double getSumma() { return summa; }

    public void setSumma(Double summa) { this.summa = summa; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    @Override
    public boolean isNew() {
        return newEntity;
    }

    public void setNew(boolean newEntity) {
        this.newEntity = newEntity;
    }
}
