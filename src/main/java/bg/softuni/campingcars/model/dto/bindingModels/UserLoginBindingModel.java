package bg.softuni.campingcars.model.dto.bindingModels;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginBindingModel {

    @NotEmpty(message = "Email cannot be empty!")
    @Email
    private String email;

    @NotEmpty(message = "")
    @Size(min = 4, max = 30, message = "Password must be between 4 and 30 characters!")
    private String password;
}
