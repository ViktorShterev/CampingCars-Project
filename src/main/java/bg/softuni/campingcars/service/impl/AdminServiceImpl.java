package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.AdminBindingModel;
import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.entity.User;
import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.repository.RoleRepository;
import bg.softuni.campingcars.repository.UserRepository;
import bg.softuni.campingcars.service.AdminService;
import bg.softuni.campingcars.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationService authenticationService;

    @Override
    public boolean addAdmin(AdminBindingModel adminBindingModel) {
        Optional<User> optionalUser = this.userRepository.findByEmail(adminBindingModel.email());
        Role role = this.roleRepository.findByRole(RoleEnum.ADMIN);

        if (optionalUser.isPresent() && !optionalUser.get().getRoles().contains(role)) {
            User user = optionalUser.get();

            user.getRoles().add(role);
            this.userRepository.save(user);

            return true;
        }
        return false;
    }

    @Override
    public List<AdminBindingModel> getAllAdmins() {
        Role role = this.roleRepository.findByRole(RoleEnum.ADMIN);

        return this.userRepository.findAllByRolesContaining(role)
                .stream()
                .filter(adminBindingModel -> !adminBindingModel.email().equals(this.authenticationService.getCurrentUser().get().getUsername()))
                .toList();
    }

    @Override
    public void removeAdmin(AdminBindingModel adminBindingModel) {
        Optional<User> optionalUser = this.userRepository.findByEmail(adminBindingModel.email());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Role role = this.roleRepository.findByRole(RoleEnum.ADMIN);

            user.getRoles().remove(role);
            this.userRepository.save(user);
        }
    }
}
