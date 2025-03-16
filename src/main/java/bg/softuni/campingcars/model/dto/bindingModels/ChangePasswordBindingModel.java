package bg.softuni.campingcars.model.dto.bindingModels;

import bg.softuni.campingcars.model.validation.FieldMatch;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@FieldMatch(
        first = "newPassword",
        second = "confirmNewPassword",
        message = "{user.register.password.match}"
)
public record ChangePasswordBindingModel(

        UUID uuid,

        @NotEmpty(message = "")
        @Size(min = 4, max = 30, message = "{user.register.password}")
        String oldPassword,

        @NotEmpty(message = "")
        @Size(min = 4, max = 30, message = "{user.register.password}")
        String newPassword,

        @NotEmpty(message = "")
        @Size(min = 4, max = 30, message = "{user.register.password}")
        String confirmNewPassword
) {
}
