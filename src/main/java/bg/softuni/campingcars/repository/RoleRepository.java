package bg.softuni.campingcars.repository;

import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(RoleEnum roleEnum);

    Set<Role> findAllByRoleIn(List<RoleEnum> roles);
}
