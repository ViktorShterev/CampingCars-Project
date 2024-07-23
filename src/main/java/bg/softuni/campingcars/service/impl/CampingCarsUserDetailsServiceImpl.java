package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CampingCarsUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public CampingCarsUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .map(CampingCarsUserDetailsServiceImpl::map)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + email + " not found"));
    }

    private static UserDetails map(bg.softuni.campingcars.model.entity.User user) {
         return User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(CampingCarsUserDetailsServiceImpl::mapped).toList())
                .build();
    }

    private static GrantedAuthority mapped(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getRole().name());
    }
}
