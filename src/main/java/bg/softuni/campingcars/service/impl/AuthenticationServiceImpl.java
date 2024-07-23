package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.UserLoginBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.UserRegistrationBindingModel;
import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.entity.User;
import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.repository.RoleRepository;
import bg.softuni.campingcars.repository.UserRepository;
import bg.softuni.campingcars.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean registerUser(UserRegistrationBindingModel userRegistrationBindingModel) {

        if (userRegistrationBindingModel == null) {
            return false;
        }

        if (this.userRepository.findByEmail(userRegistrationBindingModel.getEmail()).isPresent()) {
            return false;
        }

        if (userRegistrationBindingModel.getPassword().equals(userRegistrationBindingModel.getConfirmPassword())) {

            User user = this.modelMapper.map(userRegistrationBindingModel, User.class);

            Role role = this.roleRepository.findByRole(RoleEnum.USER);

            user.setRoles(Set.of(role));
            user.setActive(true);
            user.setCreated(LocalDateTime.now());
            user.setPassword(this.passwordEncoder.encode(userRegistrationBindingModel.getPassword()));

            this.userRepository.save(user);

            return true;
        }
        return false;
    }


}
