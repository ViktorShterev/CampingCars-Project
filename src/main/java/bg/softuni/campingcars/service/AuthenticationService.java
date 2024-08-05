package bg.softuni.campingcars.service;

import bg.softuni.campingcars.model.dto.bindingModels.UserRegistrationBindingModel;
import bg.softuni.campingcars.model.user.CampingCarsUserDetails;

import java.util.Optional;

public interface AuthenticationService {

    boolean registerUser(UserRegistrationBindingModel userRegistrationBindingModel);

    Optional<CampingCarsUserDetails> getCurrentUser();
}
