package bg.softuni.campingcars.config;

import bg.softuni.campingcars.service.AuthenticationService;
import bg.softuni.campingcars.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Configuration
public class RestConfig {

//    @Bean("genericRestClient")
//    public RestClient genericRestClient() {
//        return RestClient.create();
//    }

    @Bean("brandRestClient")
    public RestClient offersRestClient(BrandApiConfig brandsApiConfig,
                                       ClientHttpRequestInterceptor requestInterceptor) {
        return RestClient
                .builder()
                .baseUrl(brandsApiConfig.getBaseUrl())
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(requestInterceptor)
                .build();
    }

    @Bean
    public ClientHttpRequestInterceptor requestInterceptor(AuthenticationService authenticationService,
                                                           JwtService jwtService) {
        return (r, b, e) -> {
            // put the logged user details into bearer token
            authenticationService
                    .getCurrentUser()
                    .ifPresent(ccud -> {
                        String bearerToken = jwtService.generateToken(
                                ccud.getUuid().toString(),
                                Map.of(
                                        "roles",
                                        ccud.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
                                )
                        );

                        System.out.println("BEARER TOKEN: " + bearerToken);

                        r.getHeaders().setBearerAuth(bearerToken);
                    });

            return e.execute(r, b);
        };
    }

}
