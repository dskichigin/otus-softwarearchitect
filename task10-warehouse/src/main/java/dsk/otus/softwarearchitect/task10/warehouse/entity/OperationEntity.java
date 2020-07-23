package dsk.otus.softwarearchitect.task10.warehouse.entity;

public class OperationEntity {
    private String orderId = "";
    private String productId = "";
    private Integer col = 0;

    public String getOrderId() { return orderId; }

    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getProductId() { return productId; }

    public void setProductId(String productId) { this.productId = productId; }

    public Integer getCol() { return col; }

    public void setCol(Integer col) { this.col = col; }
}
