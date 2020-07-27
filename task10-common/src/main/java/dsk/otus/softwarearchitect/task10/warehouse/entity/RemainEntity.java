package dsk.otus.softwarearchitect.task10.warehouse.entity;

public class RemainEntity {
    private String productId;
    private Long col;

    public RemainEntity(String productId, Long col) {
        this.productId = productId;
        this.col = col;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getCol() {
        return col;
    }

    public void setCol(Long col) {
        this.col = col;
    }
}
