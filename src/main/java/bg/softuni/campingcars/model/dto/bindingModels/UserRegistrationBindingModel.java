package bg.softuni.campingcars.model.dto.bindingModels;

import bg.softuni.campingcars.model.validation.FieldMatch;
import bg.softuni.campingcars.model.validation.UniqueUserEmail;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "{user.register.password.match}"
)
@Getter
@Setter
public class UserRegistrationBindingModel {

        @Size(min = 2, max = 30, message = "{user.register.first.name}")
        @NotEmpty(message = "")
        String firstName;

        @Size(min = 2, max = 50, message = "{user.register.last.name}")
        @NotEmpty(message = "")
        String lastName;

        @NotEmpty(message = "{user.register.email}")
        @Email
        @UniqueUserEmail
        String email;

        @NotEmpty(message = "")
        @Size(min = 4, max = 30, message = "{user.register.password}")
        String password;

        @NotEmpty(message = "")
        @Size(min = 4, max = 30, message = "{user.register.password}")
        String confirmPassword;

        @NotNull(message = "{user.register.age}")
        @Min(value = 16, message = "")
        @Max(value = 120, message = "")
        Integer age;
}


