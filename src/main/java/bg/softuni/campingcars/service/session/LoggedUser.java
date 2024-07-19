package bg.softuni.campingcars.service.session;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component("loggedUser")
@SessionScope
@Getter
@Setter
public class LoggedUser {

    private String email;
    private String password;
    private boolean isLogged;
}
