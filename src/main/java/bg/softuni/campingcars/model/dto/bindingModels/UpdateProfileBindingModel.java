package bg.softuni.campingcars.model.dto.bindingModels;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record UpdateProfileBindingModel(

        UUID uuid,

        @Size(min = 2, max = 30, message = "{user.register.first.name}")
        @NotEmpty(message = "")
        String firstName,

        @Size(min = 2, max = 50, message = "{user.register.last.name}")
        @NotEmpty(message = "")
        String lastName,

        @NotEmpty(message = "{user.register.email}")
        @Email
        String email,

        @NotNull(message = "{user.register.age}")
        @Min(value = 16, message = "")
        @Max(value = 120, message = "")
        int age,

        @NotEmpty(message = "")
        @Size(min = 4, max = 30, message = "{user.register.password}")
        String password
) {
}
