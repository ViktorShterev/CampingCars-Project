package bg.softuni.campingcars.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseEntity {

    @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private int age;

    @Column(name = "is_active")
    private boolean isActive;

    @Column
    private LocalDateTime created;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public User setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

    public User setActive(boolean active) {
        isActive = active;
        return this;
    }

    public User setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }
}
