package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.ChangePasswordBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.UpdateProfileBindingModel;
import bg.softuni.campingcars.model.user.CampingCarsUserDetails;
import bg.softuni.campingcars.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ModelAndView profile() {
        CampingCarsUserDetails userDetails = this.userService.getUserDetails();

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("currentUser", userDetails);

        return modelAndView;
    }

    @PreAuthorize("@userServiceImpl.isCurrentUser(#uuid)")
    @GetMapping("/profile/edit/{uuid}")
    public ModelAndView editProfile(@PathVariable UUID uuid) {
        UpdateProfileBindingModel userDetailsForUpdateProfile = this.userService.getUserDetailsForUpdateProfile(uuid);

        if (userDetailsForUpdateProfile != null) {
            ModelAndView modelAndView = new ModelAndView("edit-profile");
            modelAndView.addObject("updateProfileBindingModel", userDetailsForUpdateProfile);
            return modelAndView;
        }
        return new ModelAndView("redirect:/users/login");
    }

    @PreAuthorize("@userServiceImpl.isCurrentUser(#uuid)")
    @PutMapping("/profile/edit/{uuid}")
    public ModelAndView updateProfile(@PathVariable("uuid") UUID uuid,
                               @ModelAttribute("userDetailsForUpdateProfile")
                               @Valid UpdateProfileBindingModel userDetailsForUpdateProfile,
                               BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("edit-profile");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("updateProfileBindingModel", userDetailsForUpdateProfile);
            modelAndView.addObject("org.springframework.validation.BindingResult.updateProfileBindingModel", bindingResult);
            return modelAndView;
        }

        boolean updated = this.userService.updateProfile(uuid, userDetailsForUpdateProfile);

        if (!updated) {
            modelAndView.addObject("hasErrors", true);
            modelAndView.addObject("updateProfileBindingModel", userDetailsForUpdateProfile);
            return modelAndView;
        }

        return new ModelAndView("redirect:/users/profile");
    }

    @PreAuthorize("@userServiceImpl.isCurrentUser(#uuid)")
    @GetMapping("/profile/edit/password/{uuid}")
    public ModelAndView editPassword(@PathVariable("uuid") UUID uuid) {
        ChangePasswordBindingModel changePasswordBindingModel = this.userService.getUserDetailsForChangePassword(uuid);

        if (changePasswordBindingModel != null) {
            ModelAndView modelAndView = new ModelAndView("change-password");
            modelAndView.addObject("changePasswordBindingModel", changePasswordBindingModel);
            return modelAndView;
        }
        return new ModelAndView("redirect:/users/login");
    }

    @PreAuthorize("@userServiceImpl.isCurrentUser(#uuid)")
    @PutMapping("/profile/edit/password/{uuid}")
    public ModelAndView changePassword(@PathVariable("uuid") UUID uuid,
                               @ModelAttribute("changePasswordBindingModel")
                               @Valid ChangePasswordBindingModel changePasswordBindingModel,
                               BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("change-password");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("changePasswordBindingModel", changePasswordBindingModel);
            modelAndView.addObject("org.springframework.validation.BindingResult.updateProfileBindingModel", bindingResult);
            return modelAndView;
        }

        boolean updated = this.userService.changePassword(uuid, changePasswordBindingModel);

        if (!updated) {
            modelAndView.addObject("hasErrors", true);
            modelAndView.addObject("changePasswordBindingModel", changePasswordBindingModel);
            return modelAndView;
        }

        return new ModelAndView("redirect:/users/profile");
    }
}
