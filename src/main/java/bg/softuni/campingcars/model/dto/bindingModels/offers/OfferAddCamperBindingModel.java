package bg.softuni.campingcars.model.dto.bindingModels.offers;

import bg.softuni.campingcars.model.enums.EngineEnum;
import bg.softuni.campingcars.model.enums.TransmissionEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferAddCamperBindingModel extends OfferAddCaravanBindingModel {

    @NotNull
    private EngineEnum engine;

    @NotNull
    private TransmissionEnum transmission;

    @NotNull
    @Positive
    private Long mileage;

    @NotNull
    @Positive
    private Integer horsePower;
}
