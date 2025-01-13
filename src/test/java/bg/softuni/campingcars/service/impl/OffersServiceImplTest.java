package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.views.OfferViewModel;
import bg.softuni.campingcars.model.entity.Category;
import bg.softuni.campingcars.model.entity.Model;
import bg.softuni.campingcars.model.entity.Offer;
import bg.softuni.campingcars.model.entity.User;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import bg.softuni.campingcars.model.enums.EngineEnum;
import bg.softuni.campingcars.model.enums.TransmissionEnum;
import bg.softuni.campingcars.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OffersServiceImplTest {

    private OffersServiceImpl toTest;

    @Mock
    private OfferRepository mockOfferRepository;

    @BeforeEach
    void setUp() {
        toTest = new OffersServiceImpl(mockOfferRepository);
    }

    @Test
    void testGetAllOffers() {
        Offer offer = createOffer();

        Pageable pageable = Pageable.ofSize(3);

        Page<Offer> mockOfferPage = new PageImpl<>(Collections.singletonList(offer));

        when(mockOfferRepository.findAll(pageable))
                .thenReturn(mockOfferPage);

        Page<OfferViewModel> allOffers = toTest.findAllOffers(pageable);

        assertNotNull(allOffers.getContent());
        assertEquals(allOffers.getTotalElements(), 1L);
    }

    private Offer createOffer() {
        Offer offer = new Offer();
        offer.setUuid(UUID.randomUUID())
                .setTransmission(TransmissionEnum.MANUAL)
                .setCreated(LocalDateTime.now())
                .setEngine(EngineEnum.DIESEL)
                .setDescription("test")
                .setModel(new Model().setBrandName("Audi").setName("Cool").setCategory(new Category().setCategory(CategoryEnum.CAMPER)))
                .setSeller(new User())
                .setYear(2000)
                .setBeds(1)
                .setImageUrl("blabla")
                .setCategory(new Category().setCategory(CategoryEnum.CAMPER));

        return offer;
    }
}