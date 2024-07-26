package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.UserRegistrationBindingModel;
import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.entity.User;
import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.repository.RoleRepository;
import bg.softuni.campingcars.repository.UserRepository;
import bg.softuni.campingcars.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class AuthenticationServiceImplTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRegistrationBindingModel userRegistrationBindingModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUserSuccess() {
        // Arrange
        String email = "test@example.com";
        String firstName = "Test";
        String lastName = "Test";
        int age = 20;
        String password = "password";
        String confirmPassword = "password";

        given(userRegistrationBindingModel.getEmail()).willReturn(email);
        given(userRegistrationBindingModel.getFirstName()).willReturn(firstName);
        given(userRegistrationBindingModel.getLastName()).willReturn(lastName);
        given(userRegistrationBindingModel.getAge()).willReturn(age);
        given(userRegistrationBindingModel.getPassword()).willReturn(password);
        given(userRegistrationBindingModel.getConfirmPassword()).willReturn(confirmPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Role role = new Role();
        role.setRole(RoleEnum.USER);

        when(roleRepository.findByRole(RoleEnum.USER)).thenReturn(role);

        User user = new User();
        given(modelMapper.map(userRegistrationBindingModel, User.class)).willReturn(user);

        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        // Act
        boolean result = authenticationService.registerUser(userRegistrationBindingModel);

        // Assert
        assertTrue(result);
    }

    @Test
    void testRegisterUserEmailAlreadyExists() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String confirmPassword = "password";

        given(userRegistrationBindingModel.getEmail()).willReturn(email);
        given(userRegistrationBindingModel.getPassword()).willReturn(password);
        given(userRegistrationBindingModel.getConfirmPassword()).willReturn(confirmPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        // Act
        boolean result = authenticationService.registerUser(userRegistrationBindingModel);

        // Assert
        assertFalse(result);
    }

    @Test
    void testRegisterUserPasswordMismatch() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String confirmPassword = "differentPassword";

        given(userRegistrationBindingModel.getEmail()).willReturn(email);
        given(userRegistrationBindingModel.getPassword()).willReturn(password);
        given(userRegistrationBindingModel.getConfirmPassword()).willReturn(confirmPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean result = authenticationService.registerUser(userRegistrationBindingModel);

        // Assert
        assertFalse(result);
    }

    @Test
    void testRegisterUserRoleNotFound() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String confirmPassword = "password";

        given(userRegistrationBindingModel.getEmail()).willReturn(email);
        given(userRegistrationBindingModel.getPassword()).willReturn(password);
        given(userRegistrationBindingModel.getConfirmPassword()).willReturn(confirmPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        when(roleRepository.findByRole(RoleEnum.USER)).thenReturn(null); // Role not found

        // Act
        boolean result = authenticationService.registerUser(userRegistrationBindingModel);

        // Assert
        assertFalse(result);
    }
}
