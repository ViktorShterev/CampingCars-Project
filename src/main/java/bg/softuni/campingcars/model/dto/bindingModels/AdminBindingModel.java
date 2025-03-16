package bg.softuni.campingcars.model.dto.bindingModels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AdminBindingModel(

        @NotEmpty(message = "{user.register.email}")
        @Email
        String email
) {
}
