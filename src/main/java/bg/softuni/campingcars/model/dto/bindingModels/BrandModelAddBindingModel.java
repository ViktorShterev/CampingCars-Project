package bg.softuni.campingcars.model.dto.bindingModels;

import bg.softuni.campingcars.model.enums.CategoryEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BrandModelAddBindingModel(

        @NotEmpty(message = "Brand must not be empty!")
        @Size(min = 2, max = 30, message = "")
        String brand,

        @NotEmpty(message = "Model must not be empty!")
        @Size(min = 2, max = 50, message = "")
        String model,

        @NotNull(message = "You must choose a category!")
        CategoryEnum category
) {
}
