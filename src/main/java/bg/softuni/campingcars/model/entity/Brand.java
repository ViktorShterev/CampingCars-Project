package bg.softuni.campingcars.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
@Getter
public class Brand extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand",fetch = FetchType.EAGER)
    private Set<Model> models;

    public Brand() {
        this.models = new HashSet<>();
    }

    public Brand setName(String name) {
        this.name = name;
        return this;
    }

    public Brand setModels(Set<Model> models) {
        this.models = models;
        return this;
    }
}
