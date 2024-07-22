package bg.softuni.campingcars.repository;

import bg.softuni.campingcars.model.entity.Category;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategory(CategoryEnum categoryEnum);
}
