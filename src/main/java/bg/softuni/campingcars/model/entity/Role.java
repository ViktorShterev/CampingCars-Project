package bg.softuni.campingcars.model.entity;

import bg.softuni.campingcars.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return this.role == role1.role;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(role);
    }
}
