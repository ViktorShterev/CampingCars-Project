package bg.softuni.campingcars.model.entity;

import bg.softuni.campingcars.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "roles")
@Getter
public class Role extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public Role setRole(RoleEnum role) {
        this.role = role;
        return this;
    }
}
