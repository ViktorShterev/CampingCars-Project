package bg.softuni.campingcars.web;

import bg.softuni.campingcars.testUtils.TestDataUtil;
import bg.softuni.campingcars.testUtils.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BrandControllerTestIT {

    private static final String TEST_USER1_EMAIL = "user1@email.com";
    private static final String TEST_ADMIN_EMAIL = "admin@email.com";

    @Autowired
    private TestDataUtil testDataUtil;

    @Autowired
    private UserTestDataUtil userTestDataUtil;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.testDataUtil.cleanUp();
        this.userTestDataUtil.cleanUp();
    }

    @AfterEach
    public void tearDown() {
        this.testDataUtil.cleanUp();
        this.userTestDataUtil.cleanUp();
    }

    @Test
    void testAnonymousCanSeeAllBrands() throws Exception {

        mockMvc.perform(
                get("/brand/all")
                        .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = TEST_ADMIN_EMAIL,
            roles = {"USER", "ADMIN"})
    void testAdminCanSeeAllBrands() throws Exception {

        mockMvc.perform(
                get("/brand/all")
                        .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    void testAnonymousCannotAddBrand() throws Exception {

        mockMvc.perform(
                post("/brand/add")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));
    }

    @Test
    @WithMockUser(username = TEST_USER1_EMAIL,
            roles = {"USER"})
    void testUserCannotAddBrand() throws Exception {

        mockMvc.perform(
                        post("/brand/add")
                                .with(csrf())
                ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = TEST_ADMIN_EMAIL,
            roles = {"USER", "ADMIN"})
    void testAdminCanAddBrand() throws Exception {

        mockMvc.perform(
                post("/brand/add")
                        .with(csrf())
        ).andExpect(status().isOk());
    }
}