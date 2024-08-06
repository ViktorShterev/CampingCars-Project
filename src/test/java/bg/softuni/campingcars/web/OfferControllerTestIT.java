package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.entity.Offer;
import bg.softuni.campingcars.model.entity.User;
import bg.softuni.campingcars.model.enums.EngineEnum;
import bg.softuni.campingcars.model.enums.TransmissionEnum;
import bg.softuni.campingcars.repository.OfferRepository;
import bg.softuni.campingcars.service.util.UuidGeneratorService;
import bg.softuni.campingcars.testUtils.TestDataUtil;
import bg.softuni.campingcars.testUtils.UserTestDataUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @MockBean
    private UuidGeneratorService uuidGeneratorService;

    @Autowired
    private OfferRepository offerRepository;

    @BeforeEach
    public void setUp() {
        this.testDataUtil.cleanUp();
        this.userTestDataUtil.cleanUp();
        this.offerRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        this.testDataUtil.cleanUp();
        this.userTestDataUtil.cleanUp();
        this.offerRepository.deleteAll();
    }

    // Testing delete

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

        this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        User notOwner = this.userTestDataUtil.createTestUser(TEST_USER2_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(notOwner);

        mockMvc.perform(
                delete("/offer/{uuid}", offer.getUuid())
                        .with(csrf())
        ).andExpect(status().isFound())
                .andExpect(redirectedUrlTemplate("/offers/all"));
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

    //Testing add camper

    @Test
    void testAnonymousCannotAddCamper() throws Exception {

        mockMvc.perform(
                post("/offer/add/camper")
                        .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));
    }

    @Test
    @WithMockUser(username = TEST_ADMIN_EMAIL,
            roles = {"ADMIN", "USER"})
    void testUserOrAdminCanAddCamper() throws Exception {

        UUID fixedUuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(uuidGeneratorService.generateUuid()).thenReturn(fixedUuid);

        this.testDataUtil.creatingTestModel();

        this.userTestDataUtil.createTestAdmin(TEST_ADMIN_EMAIL);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/offer/add/camper")
                                .param("modelId", String.valueOf(1L))
                                .param("price", String.valueOf(BigDecimal.valueOf(25000)))
                                .param("engine", String.valueOf(EngineEnum.DIESEL))
                                .param("transmission", String.valueOf(TransmissionEnum.AUTOMATIC))
                                .param("year", "2012")
                                .param("mileage", "50000")
                                .param("description", "Very nice camper")
                                .param("imageUrl", "https://google.com")
                                .param("beds", "5")
                                .param("horsePower", "200")
                                .with(csrf())

                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/offer/details/123e4567-e89b-12d3-a456-426614174000"));

        Offer offer = this.offerRepository.findByUuid(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).get();

        assertEquals(1L, offer.getModel().getId());
        assertEquals(BigDecimal.valueOf(25000), offer.getPrice().stripTrailingZeros().setScale(0));
        assertEquals(EngineEnum.DIESEL, offer.getEngine());
        assertEquals(TransmissionEnum.AUTOMATIC, offer.getTransmission());
        assertEquals(2012, offer.getYear());
        assertEquals(50000, offer.getMileage());
        assertEquals("Very nice camper", offer.getDescription());
        assertEquals("https://google.com", offer.getImageUrl());
        assertEquals(5, offer.getBeds());
        assertEquals(200, offer.getHorsePower());
    }

    //Testing add caravan

    @Test
    void testAnonymousCannotAddCaravan() throws Exception {

        mockMvc.perform(
                        post("/offer/add/caravan")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));
    }

    @Test
    @WithMockUser(username = TEST_ADMIN_EMAIL,
            roles = {"ADMIN", "USER"})
    void testUserOrAdminCanAddCaravan() throws Exception {

        UUID fixedUuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(uuidGeneratorService.generateUuid()).thenReturn(fixedUuid);

        this.testDataUtil.creatingTestModel();

        this.userTestDataUtil.createTestAdmin(TEST_ADMIN_EMAIL);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/offer/add/caravan")
                                .param("modelId", String.valueOf(1L))
                                .param("price", String.valueOf(BigDecimal.valueOf(25000)))
                                .param("year", "2012")
                                .param("description", "Very nice caravan")
                                .param("imageUrl", "https://google.com")
                                .param("beds", "5")
                                .with(csrf())

                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/offer/details/123e4567-e89b-12d3-a456-426614174000"));

        Offer offer = this.offerRepository.findByUuid(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).get();

        assertEquals(1L, offer.getModel().getId());
        assertEquals(BigDecimal.valueOf(25000), offer.getPrice().stripTrailingZeros().setScale(0));
        assertEquals(2012, offer.getYear());
        assertEquals("Very nice caravan", offer.getDescription());
        assertEquals("https://google.com", offer.getImageUrl());
        assertEquals(5, offer.getBeds());
    }

    //Testing offer detail

    @Test
    void testAnonymousGetOfferDetails() throws Exception {
        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        get("/offer/details/{uuid}", offer.getUuid())
                                .with(csrf())
                ).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = TEST_ADMIN_EMAIL,
            roles = {"ADMIN", "USER"})
    void testUserOrAdminGetOfferDetails() throws Exception {
        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        this.userTestDataUtil.createTestUser(TEST_ADMIN_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                get("/offer/details/{uuid}", offer.getUuid())
                        .with(csrf())
        ).andExpect(status().is2xxSuccessful());
    }

    //Testing update offer

    @Test
    void testAnonymousCannotUpdate() throws Exception {
        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        get("/offer/update/{uuid}", offer.getUuid())
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));
    }

    @Test
    @WithMockUser(username = TEST_USER1_EMAIL,
            roles = {"USER"})
    void testNonAdminUserOwnerUpdate() throws Exception {
        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        get("/offer/update/{uuid}", offer.getUuid())
                                .with(csrf())
                ).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(username = TEST_USER1_EMAIL,
            roles = {"USER"})
    void testNonAdminUserNotOwnerUpdate() throws Exception {
        this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);
        User owner = this.userTestDataUtil.createTestUser(TEST_USER2_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                get("/offer/update/{uuid}", offer.getUuid())
                        .with(csrf())
        ).andExpect(status().isFound())
                .andExpect(redirectedUrlTemplate("/offer/details/{uuid}", offer.getUuid()));
    }

    @Test
    @WithMockUser(username = TEST_ADMIN_EMAIL,
            roles = {"ADMIN", "USER"})
    void testAdminUserNotOwnerUpdate() throws Exception {
        this.userTestDataUtil.createTestAdmin(TEST_ADMIN_EMAIL);

        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        get("/offer/update/{uuid}", offer.getUuid())
                                .with(csrf())
                ).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testAnonymousCannotUpdateForm() throws Exception {
        User owner = this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        put("/offer/update/{uuid}", offer.getUuid())
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/users/login"));
    }

//    @Test
//    @WithMockUser(username = TEST_USER1_EMAIL,
//            roles = {"USER"})
//    void testNonAdminUserNotOwnerUpdateForm() throws Exception {
//        this.userTestDataUtil.createTestUser(TEST_USER1_EMAIL);
//        User owner = this.userTestDataUtil.createTestUser(TEST_USER2_EMAIL);
//
//        Offer offer = this.testDataUtil.createTestOffer(owner);
//
//        mockMvc.perform(
//                        put("/offer/update/{uuid}", offer.getUuid())
//                                .with(csrf())
//                ).andExpect(status().isFound())
//                .andExpect(redirectedUrlTemplate("/offer/details/{uuid}", offer.getUuid()));
//    }

    @Test
    @WithMockUser(username = TEST_ADMIN_EMAIL,
            roles = {"ADMIN", "USER"})
    void testUserOrAdminCanUpdate() throws Exception {

        UUID fixedUuid = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(uuidGeneratorService.generateUuid()).thenReturn(fixedUuid);

        User owner = this.userTestDataUtil.createTestAdmin(TEST_ADMIN_EMAIL);

        Offer offer = this.testDataUtil.createTestOffer(owner);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/offer/update/123e4567-e89b-12d3-a456-426614174000")
                                .param("model", "Test Model")
                                .param("price", String.valueOf(BigDecimal.valueOf(25000)))
                                .param("engine", String.valueOf(EngineEnum.DIESEL))
                                .param("transmission", String.valueOf(TransmissionEnum.AUTOMATIC))
                                .param("year", "2012")
                                .param("mileage", "50000")
                                .param("description", "Very nice camper")
                                .param("imageUrl", "https://google.com")
                                .param("beds", "5")
                                .param("horsepower", "200")
                                .with(csrf())

                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/offer/details/123e4567-e89b-12d3-a456-426614174000"));

        Offer offerUpdated = this.offerRepository.findByUuid(offer.getUuid()).get();

        assertEquals("Test Model", offerUpdated.getModel().getName());
        assertEquals(BigDecimal.valueOf(25000), offerUpdated.getPrice().stripTrailingZeros().setScale(0));
        assertEquals(EngineEnum.DIESEL, offerUpdated.getEngine());
        assertEquals(TransmissionEnum.AUTOMATIC, offerUpdated.getTransmission());
        assertEquals(2012, offerUpdated.getYear());
        assertEquals(50000, offerUpdated.getMileage());
        assertEquals("Very nice camper", offerUpdated.getDescription());
        assertEquals("https://google.com", offerUpdated.getImageUrl());
        assertEquals(5, offerUpdated.getBeds());
        assertEquals(200, offerUpdated.getHorsePower());
    }
}
