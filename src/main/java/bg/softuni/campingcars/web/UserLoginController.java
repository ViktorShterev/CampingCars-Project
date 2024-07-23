package bg.softuni.campingcars.web;

import bg.softuni.campingcars.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ModelAndView login() {
        return new ModelAndView("auth-login");
    }

    @PostMapping("/login-error")
    public String onFailure(@ModelAttribute("email") String email,
                            Model model) {

        model.addAttribute("email", email);
        model.addAttribute("bad_credentials", "true");

        return "auth-login";
    }
}
