package dsk.otus.softwarearchitect.task10.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@JsonIgnoreProperties(value = { "new" })
@Table("orders")
public class OrderEntity implements Persistable<String> {

    @Id
    @Column("id")
    private String id;
    @Column("productId")
    private String productId;
    @Column("delivery_time_from")
    private String deliveryTimeFrom;
    @Column("delivery_time_to")
    private String deliveryTimeTo;
    @Column("col")
    private Integer col;
    @Column("summa")
    private Double summa;
    @Column("status")
    private String status;


    @Transient
    private boolean newEntity = true;

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id;  }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getDeliveryTimeFrom() { return deliveryTimeFrom; }
    public void setDeliveryTimeFrom(String deliveryTimeFrom) { this.deliveryTimeFrom = deliveryTimeFrom; }

    public String getDeliveryTimeTo() { return deliveryTimeTo; }
    public void setDeliveryTimeTo(String deliveryTimeTo) { this.deliveryTimeTo = deliveryTimeTo; }

    public Integer getCol() { return col; }
    public void setCol(Integer col) { this.col = col; }

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
