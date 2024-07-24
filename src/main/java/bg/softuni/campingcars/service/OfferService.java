package bg.softuni.campingcars.service;

import bg.softuni.campingcars.model.dto.bindingModels.OfferSummaryDTO;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCamperBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCaravanBindingModel;
import bg.softuni.campingcars.model.dto.views.OfferViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface OfferService {

    UUID addCamperOffer(OfferAddCamperBindingModel offerAddCamperBindingModel, UserDetails seller);

    UUID addCaravanOffer(OfferAddCaravanBindingModel offerAddCaravanBindingModel, UserDetails seller);

    Optional<OfferSummaryDTO> getOfferDetail(UUID uuid, UserDetails viewer);

    void deleteOffer(UUID uuid);

    boolean isOwner(UUID uuid, String username);

    Page<OfferViewModel> findAllOffers(Pageable pageable);
}
