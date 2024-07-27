package bg.softuni.campingcars.web;

import bg.softuni.campingcars.repository.UserRepository;
import bg.softuni.campingcars.testUtils.UserTestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrlTemplate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserLoginControllerTestIT {

    private static final String TEST_USER1_EMAIL = "user1@email.com";
    private static final String TEST_USER2_EMAIL = "user2@email.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTestDataUtil testDataUtil;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        this.testDataUtil.createTestUser(TEST_USER1_EMAIL);
    }

    @Test
    @WithMockUser(username = TEST_USER1_EMAIL)
    void testLoginSuccess() throws Exception {

        mockMvc.perform(
                post("/users/login")
                        .with(csrf())
        ).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = TEST_USER2_EMAIL)
    void testLoginFail() throws Exception {

        mockMvc.perform(
                post("/users/login")
                        .with(csrf())
        ).andExpect(status().is2xxSuccessful())
                .andExpect(forwardedUrlTemplate("/users/login-error"));

    }
}