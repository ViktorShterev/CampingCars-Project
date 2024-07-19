package bg.softuni.campingcars.init;

import bg.softuni.campingcars.model.entity.Category;
import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.repository.CategoryRepository;
import bg.softuni.campingcars.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DBInit implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        if (this.roleRepository.count() == 0) {

            List<Role> roles = new ArrayList<>();

            Arrays.stream(RoleEnum.values())
                    .forEach(
                            role -> {
                                Role roleEntity = new Role();
                                roleEntity.setRole(role);
                                roles.add(roleEntity);
                            });

            this.roleRepository.saveAll(roles);
        }

        if (this.categoryRepository.count() == 0) {

            List<Category> categories = new ArrayList<>();

            Arrays.stream(CategoryEnum.values())
                    .forEach(
                            category -> {
                                Category categoryEntity = new Category();
                                categoryEntity.setCategory(category);
                                categories.add(categoryEntity);
                            }
                    );

            this.categoryRepository.saveAll(categories);
        }
    }
}
