package bg.softuni.campingcars.testUtils;

import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRoleDBInit implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (this.roleRepository.count() == 0) {

            this.roleRepository.saveAll(
                    List.of(
                            new Role().setRole(RoleEnum.USER),
                            new Role().setRole(RoleEnum.ADMIN)
                    )
            );
        }
    }
}
