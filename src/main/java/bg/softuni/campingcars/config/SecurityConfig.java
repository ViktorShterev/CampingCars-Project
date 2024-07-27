package bg.softuni.campingcars.config;

import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.repository.UserRepository;
import bg.softuni.campingcars.service.impl.CampingCarsUserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.authorizeHttpRequests(
//                Define which urls are visible to which users
                authorizeRequests -> authorizeRequests
//                        All static resources which are situated in js, images, css are visible to anyone
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                        Allow anyone to see the home, login and register form and page
                        .requestMatchers("/", "/about", "/users/login", "/users/register", "/users/login-error").permitAll()
                        .requestMatchers("/offers/all").permitAll()
                        .requestMatchers("/brand/all").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/offer/category", "offer/details/**", "offer/add/camper", "offer/add/caravan").permitAll()
                        .requestMatchers("/brand/add").hasRole(RoleEnum.ADMIN.name())
                        .requestMatchers("/api/currency/convert").permitAll()
//                        All other requests are authenticated
                        .anyRequest().authenticated()

        ).formLogin(
                formLogin -> {
                    formLogin
//                            Redirect here when we access something which is not allowed
//                            Also this is the page where we perform login
                            .loginPage("/users/login")
//                            The names of the input fields (in our case auth-login.html)
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureForwardUrl("/users/login-error");

                }

        ).logout(
                logout -> {
                    logout
//                            The url where we should POST something in order to perform the logout
                            .logoutUrl("/users/logout")
//                            Where to go when logged out
                            .logoutSuccessUrl("/")
//                            Invalidate the HTTP session
                            .invalidateHttpSession(true);
                }

        ).build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
//        This service translates the campingCars users and roles
//        to representation which spring security understands
        return new CampingCarsUserDetailsServiceImpl(userRepository);
    }

        @Bean
        public PasswordEncoder passwordEncoder () {
            return new BCryptPasswordEncoder();
        }
    }
