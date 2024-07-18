package bg.softuni.campingcars.model.entity;

import bg.softuni.campingcars.model.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "categories")
@Getter
public class Category extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    public CategoryEnum category;

    public Category setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }
}
