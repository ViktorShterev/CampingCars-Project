package bg.softuni.campingcars.repository;

import bg.softuni.campingcars.model.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    Optional<Model> findByName(String name);

    List<Model> findAllByBrandName(String brandName);

    void deleteAllByBrandName(String brandName);
}
