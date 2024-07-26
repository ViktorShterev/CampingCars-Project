package bg.softuni.campingcars.service.testUtils;

import bg.softuni.campingcars.model.entity.*;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import bg.softuni.campingcars.model.enums.EngineEnum;
import bg.softuni.campingcars.model.enums.TransmissionEnum;
import bg.softuni.campingcars.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Component
public class TestDataUtil {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelRepository modelRepository;

    public void createExchangeRate(String currency, BigDecimal rate) {
        this.exchangeRateRepository.save(
                new ExchangeRate().setCurrencyCode(currency).setExchangeRate(rate));
    }

    public Offer createTestOffer(User owner) {

        Category category = this.categoryRepository.findByCategory(CategoryEnum.CAMPER).get();

        Model model = new Model()
                .setName("Test Model")
                .setCategory(category);

        this.modelRepository.save(model);

        Brand brand = new Brand()
                .setName("Test Brand")
                .setModels(Set.of(model));

        this.brandRepository.save(brand);

        Offer offer = new Offer()
                .setModel(brand.getModels().stream().findFirst().get())
                .setImageUrl("https://google.com")
                .setPrice(BigDecimal.valueOf(1000))
                .setYear(2010)
                .setDescription("Test Description")
                .setEngine(EngineEnum.DIESEL)
                .setTransmission(TransmissionEnum.MANUAL)
                .setMileage(100000L)
                .setSeller(owner)
                .setUuid(UUID.randomUUID())
                .setCreated(LocalDateTime.now());

        this.offerRepository.save(offer);

        return offer;
    }

    public void cleanUp() {
        this.exchangeRateRepository.deleteAll();
        this.offerRepository.deleteAll();
        this.brandRepository.deleteAll();
        this.modelRepository.deleteAll();
    }
}
