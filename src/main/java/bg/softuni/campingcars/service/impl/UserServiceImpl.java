package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.ChangePasswordBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.UpdateProfileBindingModel;
import bg.softuni.campingcars.model.entity.User;
import bg.softuni.campingcars.model.user.CampingCarsUserDetails;
import bg.softuni.campingcars.repository.UserRepository;
import bg.softuni.campingcars.service.AuthenticationService;
import bg.softuni.campingcars.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public CampingCarsUserDetails getUserDetails() {
        return this.authenticationService.getCurrentUser().get();
    }

    @Override
    public UpdateProfileBindingModel getUserDetailsForUpdateProfile(UUID uuid) {
        if (isCurrentUser(uuid)) {
            return new UpdateProfileBindingModel(
                    getUserDetails().getUuid(),
                    getUserDetails().getFirstName(),
                    getUserDetails().getLastName(),
                    getUserDetails().getUsername(),
                    getUserDetails().getAge(),
                    getUserDetails().getPassword()
            );
        }
        return null;
    }

    @Override
    public boolean updateProfile(UUID uuid, UpdateProfileBindingModel updateProfileBindingModel) {
        if (isCurrentUser(uuid) && updateProfileBindingModel != null
                && this.userRepository.findByUuid(uuid).isPresent()) {

            User user = this.userRepository.findByUuid(uuid).get();

            if (this.passwordEncoder.matches(updateProfileBindingModel.password(), user.getPassword())) {

                if (!user.getEmail().equals(updateProfileBindingModel.email())) {
                    boolean present = this.userRepository.findByEmail(updateProfileBindingModel.email()).isPresent();

                    if (present) {
                        return false;
                    }

                    user.setEmail(updateProfileBindingModel.email());
                }
                user.setFirstName(updateProfileBindingModel.firstName());
                user.setLastName(updateProfileBindingModel.lastName());
                user.setAge(updateProfileBindingModel.age());

                this.userRepository.save(user);
                UserDetails updatedUserDetails = this.userDetailsService.loadUserByUsername(user.getEmail());

                Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUserDetails, updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return true;
            }
        }
        return false;
    }

    @Override
    public ChangePasswordBindingModel getUserDetailsForChangePassword(UUID uuid) {
        if (isCurrentUser(uuid)) {
            return new ChangePasswordBindingModel(
                    getUserDetails().getUuid(),
                    getUserDetails().getPassword(),
                    getUserDetails().getPassword(),
                    getUserDetails().getPassword()
            );
        }
        return null;
    }

    @Override
    public boolean changePassword(UUID uuid, ChangePasswordBindingModel changePasswordBindingModel) {
        if (isCurrentUser(uuid) && changePasswordBindingModel != null
                && this.userRepository.findByUuid(uuid).isPresent()) {

            User user = this.userRepository.findByUuid(uuid).get();

            if (this.passwordEncoder.matches(changePasswordBindingModel.oldPassword(), user.getPassword())
            && changePasswordBindingModel.newPassword().equals(changePasswordBindingModel.confirmNewPassword())
            && !this.passwordEncoder.matches(changePasswordBindingModel.newPassword(), user.getPassword())) {

                user.setPassword(passwordEncoder.encode(changePasswordBindingModel.newPassword()));
                this.userRepository.save(user);

                UserDetails updatedUserDetails = this.userDetailsService.loadUserByUsername(user.getEmail());

                Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUserDetails, updatedUserDetails.getPassword(), updatedUserDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                return true;
            }
            return false;
        }
        return false;
    }

    public boolean isCurrentUser(UUID uuid) {
        return this.authenticationService.getCurrentUser().get().getUuid().equals(uuid);
    }
}
