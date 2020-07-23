package dsk.otus.softwarearchitect.task10.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@JsonIgnoreProperties(value = { "new" })
@Table("warehouses")
public class WarehouseEntity implements Persistable<String> {

    @Id
    @Column("id")
    private String id;
    @Column("orderId")
    private String orderId;
    @Column("productId")
    private String productId;
    @Column("col")
    private Integer col;
    @Column("type")
    private String type;
    @Column("active")
    private Integer active;

    @Transient
    private boolean newEntity = true;

    @Override
    public String getId() { return id; }
    public void setId(String id) { this.id = id;  }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public Integer getCol() { return col; }
    public void setCol(Integer col) { this.col = col; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getActive() { return active; }
    public void setActive(Integer active) { this.active = active; }

    @Override
    public boolean isNew() {
        return newEntity;
    }
    public void setNew(boolean newEntity) {
        this.newEntity = newEntity;
    }
}
