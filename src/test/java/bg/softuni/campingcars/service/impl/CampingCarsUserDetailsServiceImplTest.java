package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.entity.Role;
import bg.softuni.campingcars.model.entity.User;
import bg.softuni.campingcars.model.enums.RoleEnum;
import bg.softuni.campingcars.model.user.CampingCarsUserDetails;
import bg.softuni.campingcars.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CampingCarsUserDetailsServiceImplTest {

    private CampingCarsUserDetailsServiceImpl serviceTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        this.serviceTest = new CampingCarsUserDetailsServiceImpl(this.mockUserRepository);
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> this.serviceTest.loadUserByUsername("example@test.com"));
    }

    @Test
    void testUserFound() {
        // Arrange
        User testUser = createTestUser();

        when(this.mockUserRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        // Act
        UserDetails userDetails = this.serviceTest.loadUserByUsername(testUser.getEmail());

        // Assert
        Assertions.assertNotNull(userDetails);
        Assertions.assertInstanceOf(CampingCarsUserDetails.class, userDetails);

        CampingCarsUserDetails campingCarsUserDetails = (CampingCarsUserDetails) userDetails;

        Assertions.assertEquals(testUser.getUuid(), campingCarsUserDetails.getUuid());
        Assertions.assertEquals(testUser.getEmail(), campingCarsUserDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(), campingCarsUserDetails.getPassword());
        Assertions.assertEquals(testUser.getFirstName(), campingCarsUserDetails.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), campingCarsUserDetails.getLastName());
        Assertions.assertFalse(testUser.isActive());

        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        Assertions.assertTrue(
                containsAuthority(userDetails, "ROLE_" + RoleEnum.ADMIN),
                "The user is not admin!");

        Assertions.assertTrue(
                containsAuthority(userDetails, "ROLE_" + RoleEnum.USER),
                "The user is not user!");

    }

    private boolean containsAuthority(UserDetails userDetails, String authority) {
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }


//    @Test
//    void testMock() {
//
//        User userEntity = new User()
//                .setFirstName("Anna");
//
//        when(this.mockUserRepository.findByEmail("voltron773@gmail.com"))
//                .thenReturn(Optional.of(userEntity));
//
//        Optional<User> userOpt = this.mockUserRepository.findByEmail("voltron773@gmail.com");
//
//        User user = userOpt.get();
//
//        Assertions.assertEquals("Anna", user.getFirstName());
//    }

    private static User createTestUser() {
        return new User()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("email@test.com")
                .setPassword("password")
                .setActive(false)
                .setRoles(Set.of(
                        new Role().setRole(RoleEnum.ADMIN),
                        new Role().setRole(RoleEnum.USER)
                ));
    }
}