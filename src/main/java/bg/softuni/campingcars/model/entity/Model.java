package bg.softuni.campingcars.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "models")
@Getter
public class Model extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

    public Model setName(String name) {
        this.name = name;
        return this;
    }

    public Model setBrand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public Model setCategory(Category category) {
        this.category = category;
        return this;
    }
}
