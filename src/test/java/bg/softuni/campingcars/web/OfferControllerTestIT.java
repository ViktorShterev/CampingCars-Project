package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.entity.Offer;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferControllerTestIT {

    private static final String TEST_USER1_EMAIL = "user1@email.com";
    private static final String TEST_USER2_EMAIL = "user2@email.com";

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
    void testAnonymousDeletionFails() throws Exception {
        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        delete("/offer/{uuid}", offer.getUuid())
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));
    }

    @Test
    @WithMockUser(username = TEST_USER1_EMAIL,
            roles = {"USER"})
    void testNonAdminUserOwnedOffer() throws Exception {
        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        delete("/offer/{uuid}", offer.getUuid())
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/offers/all"));
    }

    @Test
    @WithMockUser(username = TEST_USER1_EMAIL,
            roles = {"USER"})
    void testNonAdminUserNotOwnedOffer() throws Exception {

        User notOwner = this.userTestDataUtil.createTestUser(TEST_USER2_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(notOwner);

        mockMvc.perform(
                delete("/offer/{uuid}", offer.getUuid())
                        .with(csrf())
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = TEST_ADMIN_EMAIL,
        roles = {"ADMIN", "USER"})
    void testAdminUserNotOwnedOffer() throws Exception {
        this.userTestDataUtil.createTestAdmin(TEST_ADMIN_EMAIL);

        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        delete("/offer/{uuid}", offer.getUuid())
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/offers/all"));
    }

}
