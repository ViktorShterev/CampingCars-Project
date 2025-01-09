package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.model.user.CampingCarsUserDetails;
import bg.softuni.campingcars.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        return new CampingCarsUserDetails(
                user.getUuid(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(Role::getRole).map(CampingCarsUserDetailsServiceImpl::mapped).toList(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    private static GrantedAuthority mapped(RoleEnum role) {
        return new SimpleGrantedAuthority("ROLE_" + role);
    }
}
