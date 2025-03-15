package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.UpdateOfferBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.UpdateProfileBindingModel;
import bg.softuni.campingcars.model.user.CampingCarsUserDetails;
import bg.softuni.campingcars.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ModelAndView update(@PathVariable("uuid") UUID uuid,
                               @ModelAttribute("userDetailsForUpdateProfile")
                               @Valid UpdateProfileBindingModel userDetailsForUpdateProfile,
                               BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("edit-profile");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("updateProfileBindingModel", userDetailsForUpdateProfile);
            modelAndView.addObject("org.springframework.validation.BindingResult.updateProfileBindingModel", bindingResult);
            return modelAndView;
        }

        String updated = this.userService.updateProfile(uuid, userDetailsForUpdateProfile);

        if (updated != null) {
            modelAndView.addObject("hasErrors", true);
            modelAndView.addObject("updateProfileBindingModel", userDetailsForUpdateProfile);
            return modelAndView;
        }

        return new ModelAndView("redirect:/users/profile");
    }
}
