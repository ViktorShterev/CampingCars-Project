package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.UserRegistrationBindingModel;
import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.entity.User;
import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.repository.RoleRepository;
import bg.softuni.campingcars.repository.UserRepository;
import bg.softuni.campingcars.service.AuthenticationService;
import bg.softuni.campingcars.service.util.UuidGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthenticationServiceImplTest {

    private AuthenticationService toTestAuthenticationService;

    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleRepository mockRoleRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    private UuidGeneratorService uuidGeneratorService;

    @BeforeEach
    void setUp() {
        toTestAuthenticationService = new AuthenticationServiceImpl(
                mockUserRepository,
                modelMapper = new ModelMapper(),
                mockRoleRepository,
                mockPasswordEncoder,
                uuidGeneratorService = new UuidGeneratorService()
        );
    }

    private UserRegistrationBindingModel createUserRegistrationBindingModel() {
        UserRegistrationBindingModel userRegistrationBindingModel = new UserRegistrationBindingModel();
        userRegistrationBindingModel.setFirstName("firstName");
        userRegistrationBindingModel.setLastName("lastName");
        userRegistrationBindingModel.setEmail("email");
        userRegistrationBindingModel.setAge(20);
        userRegistrationBindingModel.setPassword("password");
        userRegistrationBindingModel.setConfirmPassword("password");
        return userRegistrationBindingModel;
    }

    @Test
    void testRegisterUserFailedDTOEmpty() {
        UserRegistrationBindingModel userRegistrationBindingModel = null;

        Assertions.assertFalse(toTestAuthenticationService.registerUser(userRegistrationBindingModel));
    }

    @Test
    void testRegisterUserSuccess() {
        UserRegistrationBindingModel userRegistrationBindingModel = createUserRegistrationBindingModel();
        User user = modelMapper.map(userRegistrationBindingModel, User.class);

        when(mockRoleRepository.findByRole(RoleEnum.USER))
                .thenReturn(new Role().setRole(RoleEnum.USER));

        when(mockUserRepository.save(user)).thenReturn(user);

        when(mockPasswordEncoder.encode(userRegistrationBindingModel.getPassword()))
                .thenReturn(userRegistrationBindingModel.getPassword() + userRegistrationBindingModel.getPassword());

        boolean registered = toTestAuthenticationService.registerUser(userRegistrationBindingModel);
        Assertions.assertTrue(registered);
    }

    @Test
    void testRegisterUserEmailAlreadyExists() {
        UserRegistrationBindingModel userRegistrationBindingModel = createUserRegistrationBindingModel();
        User user = modelMapper.map(userRegistrationBindingModel, User.class);

        when(mockUserRepository.findByEmail(userRegistrationBindingModel.getEmail()))
                .thenReturn(Optional.of(user));

        boolean registered = toTestAuthenticationService.registerUser(userRegistrationBindingModel);
        Assertions.assertFalse(registered);
    }

    @Test
    void testRegisterUserPasswordMismatch() {

        UserRegistrationBindingModel userRegistrationBindingModel = createUserRegistrationBindingModel();
        userRegistrationBindingModel.setPassword("wrongPassword");

        boolean registered = toTestAuthenticationService.registerUser(userRegistrationBindingModel);

        Assertions.assertFalse(registered);
    }

    @Test
    void testRegisterUserRoleNotFound() {
        UserRegistrationBindingModel userRegistrationBindingModel = createUserRegistrationBindingModel();
        User user = modelMapper.map(userRegistrationBindingModel, User.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> toTestAuthenticationService.registerUser(userRegistrationBindingModel));
    }
}
