package bg.softuni.campingcars.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "models")
@Getter
public class Model extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, name = "brand_name")
    private String brandName;

    @ManyToOne
    private Category category;

    public Model setName(String name) {
        this.name = name;
        return this;
    }

    public Model setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public Model setCategory(Category category) {
        this.category = category;
        return this;
    }
}
