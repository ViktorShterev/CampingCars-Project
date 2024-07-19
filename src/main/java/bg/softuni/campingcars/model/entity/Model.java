package bg.softuni.campingcars.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "models")
@Getter
public class Model extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "manufactured_year_start", nullable = false)
    private int manufacturedYearStart;

    @Column(name = "manufactured_year_end", nullable = false)
    private int manufacturedYearEnd;

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

    public Model setManufacturedYearStart(int manufacturedYearStart) {
        this.manufacturedYearStart = manufacturedYearStart;
        return this;
    }

    public Model setManufacturedYearEnd(int manufacturedYearEnd) {
        this.manufacturedYearEnd = manufacturedYearEnd;
        return this;
    }
}
