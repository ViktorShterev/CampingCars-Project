package bg.softuni.campingcars.model.dto.bindingModels;

import bg.softuni.campingcars.model.enums.CategoryEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BrandModelAddBindingModel(

        @NotEmpty(message = "")
        @Size(min = 2, max = 30, message = "{add.brand.length}")
        String brand,

        @NotEmpty(message = "")
        @Size(min = 2, max = 50, message = "{add.brand.model.length}")
        String model,

        @NotNull(message = "{add.brand.category.length}")
        CategoryEnum category
) {
}
