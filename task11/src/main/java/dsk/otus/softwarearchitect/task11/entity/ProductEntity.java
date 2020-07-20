package dsk.otus.softwarearchitect.task11.entity;

public class ProductEntity {

    private String guid;
    private String name;
    private String category;
    private String color;
    private Double price;
    private String description;
    private Integer shard;

    public String getGuid() { return guid; }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getShard() {
        return shard;
    }

    public void setShard(Integer shard) {
        this.shard = shard;
    }
}
