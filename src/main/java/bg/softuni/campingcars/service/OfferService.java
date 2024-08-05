package bg.softuni.campingcars.service;

import bg.softuni.campingcars.model.dto.bindingModels.OfferSummaryDTO;
import bg.softuni.campingcars.model.dto.bindingModels.UpdateOfferBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCamperBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCaravanBindingModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface OfferService {

    UUID addCamperOffer(OfferAddCamperBindingModel offerAddCamperBindingModel, UserDetails seller);

    UUID addCaravanOffer(OfferAddCaravanBindingModel offerAddCaravanBindingModel, UserDetails seller);

    Optional<OfferSummaryDTO> getOfferDetail(UUID uuid, UserDetails viewer);

    void deleteOffer(UUID uuid, UserDetails principal);

    boolean isOwner(UUID uuid, String username);

    void updateOffer(UUID uuid, UpdateOfferBindingModel updateOfferBindingModel, UserDetails principal);

    UpdateOfferBindingModel getOfferForUpdate(UUID uuid, UserDetails viewer);
}
