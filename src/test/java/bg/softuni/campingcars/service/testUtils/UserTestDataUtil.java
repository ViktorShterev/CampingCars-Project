package bg.softuni.campingcars.service.testUtils;

import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.entity.User;
import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.repository.RoleRepository;
import bg.softuni.campingcars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
public class UserTestDataUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User createTestUser(String email) {
        return createUser(email, List.of(RoleEnum.USER));
    }

    public User createTestAdmin(String email) {
        return createUser(email, List.of(RoleEnum.ADMIN, RoleEnum.USER));
    }

    public User createUser(String email, List<RoleEnum> roles) {

        Set<Role> allRoles = this.roleRepository.findAllByRoleIn(roles);

        User user = new User()
                .setActive(true)
                .setEmail(email)
                .setPassword("topsecret")
                .setCreated(LocalDateTime.now())
                .setFirstName("Test First Name")
                .setLastName("Test Last Name")
                .setRoles(allRoles);

        this.userRepository.save(user);

        return user;
    }

    public void cleanUp() {
        this.userRepository.deleteAll();
    }
}
