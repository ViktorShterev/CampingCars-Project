package bg.softuni.campingcars.repository;

import bg.softuni.campingcars.model.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    Optional<Offer> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    List<Offer> findAllByModelIdIn(List<Long> modelId);

    Page<Offer> findAllBySeller_Email(String email, Pageable pageable);
}
