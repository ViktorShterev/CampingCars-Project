package bg.softuni.campingcars.service;

import bg.softuni.campingcars.model.dto.bindingModels.ChangePasswordBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.UpdateProfileBindingModel;
import bg.softuni.campingcars.model.user.CampingCarsUserDetails;

import java.util.UUID;

public interface UserService {

    CampingCarsUserDetails getUserDetails();

    UpdateProfileBindingModel getUserDetailsForUpdateProfile(UUID uuid);

    boolean updateProfile(UUID uuid, UpdateProfileBindingModel updateProfileBindingModel);

    ChangePasswordBindingModel getUserDetailsForChangePassword(UUID uuid);

    boolean changePassword(UUID uuid, ChangePasswordBindingModel changePasswordBindingModel);
}
