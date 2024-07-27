package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.entity.User;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OffersControllerTestIT {

    private static final String TEST_USER1_EMAIL = "user1@email.com";

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
    void testAnonymousGetAllOffers() throws Exception {
        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                get("/offers/all")
                        .with(csrf())
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = TEST_USER1_EMAIL,
            roles = {"USER", "ADMIN"})
    void testAdminOrUserGetAllOffers() throws Exception {
        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                get("/offers/all")
                        .with(csrf())
        ).andExpect(status().isOk());
    }
}