package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.OfferSummaryDTO;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCamperBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCaravanBindingModel;
import bg.softuni.campingcars.model.dto.views.OfferViewModel;
import bg.softuni.campingcars.model.entity.*;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.repository.CategoryRepository;
import bg.softuni.campingcars.repository.ModelRepository;
import bg.softuni.campingcars.repository.OfferRepository;
import bg.softuni.campingcars.repository.UserRepository;
import bg.softuni.campingcars.service.OfferService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final ModelRepository modelRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public UUID addCamperOffer(OfferAddCamperBindingModel offerAddCamperBindingModel, UserDetails seller) {
        return mapDTOtoOffer(offerAddCamperBindingModel, seller);
    }

    @Override
    public UUID addCaravanOffer(OfferAddCaravanBindingModel offerAddCaravanBindingModel, UserDetails seller) {
        return mapDTOtoOffer(offerAddCaravanBindingModel, seller);
    }

    @Override
    public Optional<OfferSummaryDTO> getOfferDetail(UUID uuid, UserDetails viewer) {
        return this.offerRepository
                .findByUuid(uuid)
                .map(offer -> this.mapAsSummary(offer, viewer));
    }

    @Override
    @Transactional
    public void deleteOffer(UUID uuid) {
        this.offerRepository.deleteByUuid(uuid);
    }

    @Override
    public boolean isOwner(UUID uuid, String username) {
        Offer offer = this.offerRepository.findByUuid(uuid)
                .orElse(null);

        return isOwner(offer, username);
    }

    @Override
    public List<OfferViewModel> findAllOffers() {
        return this.offerRepository.findAll()
                .stream()
                .map(this::mapped)
                .toList();
    }

    private OfferViewModel mapped(Offer offer) {
        return new OfferViewModel(
                offer.getUuid().toString(),
                offer.getModel().getBrand().getName(),
                offer.getModel().getName(),
                offer.getCategory().toString(),
                offer.getYear(),
                offer.getPrice(),
                offer.getImageUrl()
        );
    }

    private OfferSummaryDTO mapAsSummary(Offer offer, UserDetails viewer) {
        return new OfferSummaryDTO(
                offer.getUuid().toString(),
                offer.getModel().getBrand().getName(),
                offer.getModel().getName(),
                offer.getDescription(),
                offer.getCategory().toString(),
                offer.getYear(),
                offer.getMileage(),
                offer.getImageUrl(),
                offer.getPrice(),
                offer.getEngine(),
                offer.getTransmission(),
                offer.getSeller().getFirstName() + " " + offer.getSeller().getLastName(),
                isOwner(offer, viewer != null ? viewer.getUsername() : null),
                offer.getCreated().toString()
        );
    }

    private boolean isOwner(Offer offer, String username) {

        if (offer == null || username == null) {
//          anonymous users own no offers
//          missing offers are meaningless
            return false;
        }

        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (isAdmin(user)) {
            //all admins own all offers
            return true;
        }

        return user.getId().equals(offer.getSeller().getId());
    }

    private boolean isAdmin(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getRole)
                .anyMatch(r -> RoleEnum.ADMIN == r);
    }

    private <T> UUID mapDTOtoOffer(T offerAddBindingModel, UserDetails seller) {
        if (offerAddBindingModel != null) {
            Offer offer = this.modelMapper.map(offerAddBindingModel, Offer.class);

            Long modelId = 0L;
            CategoryEnum category = null;

            if (offerAddBindingModel instanceof OfferAddCamperBindingModel) {
                modelId = ((OfferAddCamperBindingModel) offerAddBindingModel).getModelId();
                category = CategoryEnum.CAMPER;

            } else if (offerAddBindingModel instanceof OfferAddCaravanBindingModel) {
                modelId = ((OfferAddCaravanBindingModel) offerAddBindingModel).getModelId();
                category = CategoryEnum.CARAVAN;
            }

            Model model = this.modelRepository.findById(modelId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Model"));

            User user = this.userRepository.findByEmail(seller.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

            Category categoryEntity = this.categoryRepository.findByCategory(category)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Category"));

            offer.setModel(model);
            offer.setSeller(user);
            offer.setCategory(categoryEntity);
            offer.setCreated(LocalDateTime.now());
            offer.setUuid(UUID.randomUUID());

            this.offerRepository.save(offer);

            return offer.getUuid();
        }
        return null;
    }
}
