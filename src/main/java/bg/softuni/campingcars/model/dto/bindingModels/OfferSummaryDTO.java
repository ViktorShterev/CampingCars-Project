package bg.softuni.campingcars.model.dto.bindingModels;

import bg.softuni.campingcars.model.enums.EngineEnum;
import bg.softuni.campingcars.model.enums.TransmissionEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OfferSummaryDTO(
        String uuid,
        String brand,
        String model,
        String description,
        String category,
        int year,
        Long mileage,
        String image,
        BigDecimal price,
        EngineEnum engine,
        TransmissionEnum transmission,
        String seller,
        boolean viewerIsOwner,
        LocalDateTime created
) {
    public String summary() {
        return brand + " " + model + ", " + year;
    }
}