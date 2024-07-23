package bg.softuni.campingcars.model.dto.views;

import java.math.BigDecimal;

public record OfferViewModel(
        String uuid,
        String brand,
        String model,
        String category,
        int year,
        BigDecimal price,
        String imageUrl
) {
}
