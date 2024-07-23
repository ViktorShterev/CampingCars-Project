package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.UserRegistrationBindingModel;
import bg.softuni.campingcars.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRegisterController {

    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public ModelAndView userRegister(@ModelAttribute("userRegistrationBindingModel") UserRegistrationBindingModel userRegistrationBindingModel) {
        return new ModelAndView("auth-register");
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("userRegistrationBindingModel")
                                 @Valid UserRegistrationBindingModel userRegistrationBindingModel,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("auth-register");
        }

        boolean isSuccessfulRegistered = this.authenticationService.registerUser(userRegistrationBindingModel);

        if (isSuccessfulRegistered) {
            return new ModelAndView("redirect:/users/login");
        }

        ModelAndView modelAndView = new ModelAndView("auth-register");
        modelAndView.addObject("hasErrors", true);

        return modelAndView;
    }
}
