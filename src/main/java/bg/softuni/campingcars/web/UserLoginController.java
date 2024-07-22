package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.UserLoginBindingModel;
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
public class UserLoginController {

    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public ModelAndView login(@ModelAttribute("userLoginBindingModel")UserLoginBindingModel userLoginBindingModel) {
        return new ModelAndView("auth-login");
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("userLoginBindingModel")
                              @Valid UserLoginBindingModel userLoginBindingModel,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("auth-login");
        }

        boolean isSuccessfulLogged = this.authenticationService.loginUser(userLoginBindingModel);

        if (isSuccessfulLogged) {
            return new ModelAndView("redirect:/");
        }

        ModelAndView modelAndView = new ModelAndView("auth-login");
        modelAndView.addObject("hasErrors", true);

        return modelAndView;
    }

    @PostMapping("/logout")
    public ModelAndView logout() {
        this.authenticationService.logoutUser();

        return new ModelAndView("redirect:/");
    }
}
