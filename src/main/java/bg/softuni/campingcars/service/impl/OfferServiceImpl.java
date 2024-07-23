package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCamperBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCaravanBindingModel;
import bg.softuni.campingcars.model.entity.Category;
import bg.softuni.campingcars.model.entity.Model;
import bg.softuni.campingcars.model.entity.Offer;
import bg.softuni.campingcars.model.entity.User;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import bg.softuni.campingcars.repository.CategoryRepository;
import bg.softuni.campingcars.repository.ModelRepository;
import bg.softuni.campingcars.repository.OfferRepository;
import bg.softuni.campingcars.repository.UserRepository;
import bg.softuni.campingcars.service.OfferService;
import bg.softuni.campingcars.service.session.LoggedUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final ModelRepository modelRepository;
    private final LoggedUser loggedUser;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void addCamperOffer(OfferAddCamperBindingModel offerAddCamperBindingModel) {

        mapDTOtoOffer(offerAddCamperBindingModel);
    }

    @Override
    public void addCaravanOffer(OfferAddCaravanBindingModel offerAddCaravanBindingModel) {

        mapDTOtoOffer(offerAddCaravanBindingModel);
    }

    private <T> void mapDTOtoOffer(T offerAddBindingModel) {
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

            User user = this.userRepository.findByEmail(this.loggedUser.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid User"));

            Category categoryEntity = this.categoryRepository.findByCategory(category)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Category"));

            offer.setModel(model);
            offer.setSeller(user);
            offer.setCategory(categoryEntity);
            offer.setCreated(LocalDateTime.now());

            this.offerRepository.save(offer);
        }
    }
}
