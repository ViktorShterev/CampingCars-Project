package bg.softuni.campingcars.testUtils;

import bg.softuni.campingcars.model.entity.Category;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import bg.softuni.campingcars.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDBInit implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        if (this.categoryRepository.count() == 0) {

            this.categoryRepository.saveAll(
                    List.of(
                            new Category().setCategory(CategoryEnum.CAMPER),
                            new Category().setCategory(CategoryEnum.CARAVAN)
                    )
            );
        }
    }
}
