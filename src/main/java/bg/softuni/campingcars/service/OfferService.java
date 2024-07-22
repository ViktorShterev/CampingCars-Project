package bg.softuni.campingcars.service;

import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCamperBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCaravanBindingModel;

public interface OfferService {

    void addCamperOffer(OfferAddCamperBindingModel offerAddCamperBindingModel);

    void addCaravanOffer(OfferAddCaravanBindingModel offerAddCaravanBindingModel);
}
