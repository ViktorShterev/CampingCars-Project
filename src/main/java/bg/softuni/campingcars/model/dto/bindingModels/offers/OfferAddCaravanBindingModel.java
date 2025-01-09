package bg.softuni.campingcars.model.dto.bindingModels.offers;

import bg.softuni.campingcars.model.validation.YearNotInTheFuture;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OfferAddCaravanBindingModel {

    @NotNull
    @Positive
    private Long modelId;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotEmpty
    @Size(min = 5, max = 512)
    private String description;

    @NotEmpty
    private String imageUrl;

    @YearNotInTheFuture
    @NotNull
    @Min(1930)
    private Integer year;

    @NotNull
    @Positive
    private Integer beds;
}
