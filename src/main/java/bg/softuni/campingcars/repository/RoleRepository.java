package bg.softuni.campingcars.repository;

import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(RoleEnum roleEnum);
}
