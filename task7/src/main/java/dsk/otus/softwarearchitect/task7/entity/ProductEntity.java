package dsk.otus.softwarearchitect.task7.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@JsonIgnoreProperties(value = { "new" })
@Table("products")
public class ProductEntity implements Persistable<Long> {

    @Id
    @Column("id")
    private Long id;
    @Column("name")
    private String name;
    @Column("category")
    private String category;
    @Column("color")
    private String color;
    @Column("price")
    private Double price;
    @Column("description")
    private String description;

    @Transient
    private boolean newEntity = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean isNew() {
        return newEntity;
    }

    public void setNew(boolean newEntity) {
        this.newEntity = newEntity;
    }
}
