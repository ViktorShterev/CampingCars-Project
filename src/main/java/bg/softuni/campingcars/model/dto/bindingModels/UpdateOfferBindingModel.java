package bg.softuni.campingcars.model.dto.bindingModels;

import bg.softuni.campingcars.model.enums.EngineEnum;
import bg.softuni.campingcars.model.enums.TransmissionEnum;
import bg.softuni.campingcars.model.validation.YearNotInTheFuture;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateOfferBindingModel(

        String uuid,
        String category,

        @NotNull
        String model,

        @NotEmpty
        @Size(min = 5, max = 512)
        String description,

        @YearNotInTheFuture
        @NotNull
        @Min(1930)
        int year,

        @NotNull
        @Positive
        int beds,

        Long mileage,

        Integer horsepower,

        @NotEmpty
        String imageUrl,

        @NotNull
        @Positive
        BigDecimal price,

        EngineEnum engine,

        TransmissionEnum transmission
) {

}
