package bg.softuni.campingcars.testUtils;

import bg.softuni.campingcars.model.entity.*;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import bg.softuni.campingcars.model.enums.EngineEnum;
import bg.softuni.campingcars.model.enums.TransmissionEnum;
import bg.softuni.campingcars.repository.CategoryRepository;
import bg.softuni.campingcars.repository.ExchangeRateRepository;
import bg.softuni.campingcars.repository.ModelRepository;
import bg.softuni.campingcars.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TestDataUtil {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelRepository modelRepository;

    public void createExchangeRate(String currency, BigDecimal rate) {
        this.exchangeRateRepository.save(
                new ExchangeRate().setCurrencyCode(currency).setExchangeRate(rate));
    }

    public Offer createTestOffer(User owner) {

        Model model = creatingTestModel();

        Offer offer = new Offer()
                .setModel(model)
                .setImageUrl("https://google.com")
                .setPrice(BigDecimal.valueOf(1000))
                .setYear(2010)
                .setDescription("Test Description")
                .setEngine(EngineEnum.DIESEL)
                .setTransmission(TransmissionEnum.MANUAL)
                .setMileage(100000L)
                .setSeller(owner)
                .setUuid(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                .setCreated(LocalDateTime.now())
                .setCategory(model.getCategory())
                .setHorsePower(200);

        this.offerRepository.save(offer);

        return offer;
    }

    public Model creatingTestModel() {
        Category category = this.categoryRepository.findByCategory(CategoryEnum.CAMPER).get();

        Model model = new Model()
                .setName("Test Model")
                .setCategory(category)
                .setBrandName("Test Brand");


        this.modelRepository.save(model);

        return model;
    }

    public void cleanUp() {
        this.exchangeRateRepository.deleteAll();
        this.offerRepository.deleteAll();
        this.modelRepository.deleteAll();
    }
}
