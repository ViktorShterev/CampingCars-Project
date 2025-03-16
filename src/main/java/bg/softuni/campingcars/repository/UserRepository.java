package bg.softuni.campingcars.repository;

import bg.softuni.campingcars.model.dto.bindingModels.AdminBindingModel;
import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUuid(UUID uuid);

    List<AdminBindingModel> findAllByRolesContaining(Role role);
}
