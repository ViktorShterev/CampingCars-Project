package bg.softuni.campingcars.service;

import bg.softuni.campingcars.model.dto.bindingModels.UserLoginBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.UserRegistrationBindingModel;

public interface AuthenticationService {

    boolean registerUser(UserRegistrationBindingModel userRegistrationBindingModel);

//    boolean loginUser(UserLoginBindingModel userLoginBindingModel);
//
//    void logoutUser();
}
