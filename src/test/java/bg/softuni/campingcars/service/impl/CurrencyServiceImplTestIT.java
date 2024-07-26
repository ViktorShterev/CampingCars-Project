package bg.softuni.campingcars.service.impl;


import bg.softuni.campingcars.model.dto.bindingModels.ExchangeRateDTO;
import bg.softuni.campingcars.model.entity.ExchangeRate;
import bg.softuni.campingcars.repository.ExchangeRateRepository;
import bg.softuni.campingcars.service.CurrencyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CurrencyServiceImplTestIT {

    @Autowired
    private CurrencyService currencyServiceToTest;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @BeforeEach
    void setUp() {
        this.exchangeRateRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        this.exchangeRateRepository.deleteAll();
    }

    @ParameterizedTest(name = "Conversion between BGN/USD exRate {0}, expected {1}")
    @MethodSource("test_Data_BGN_TO_USD")
    void test_BGN_TO_USD(Double exchangeRate,
                         Double expectedValue) {

        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO("USD", Map.of("BGN", BigDecimal.valueOf(exchangeRate)));

        this.currencyServiceToTest.refreshRates(exchangeRateDTO);

        Optional<ExchangeRate> exRateUSD_BGN = this.exchangeRateRepository.findById("USD");

        assertTrue(exRateUSD_BGN.isPresent());

        assertEquals(
                BigDecimal.valueOf(expectedValue).setScale(2, RoundingMode.DOWN),
                exRateUSD_BGN.get().getExchangeRate());
    }

//    @ParameterizedTest(name = "Conversion between BGN/EUR exRateBGN {0}, exRateEUR {1}, expected {2}")
//    @MethodSource("test_Data_BGN_TO_EUR")
//    void test_BGN_TO_EUR(Double exchangeRateBGN,
//                         Double exchangeRateEur,
//                         Double expectedValue) {
//
//        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO("USD", Map.of(
//                "BGN", BigDecimal.valueOf(exchangeRateBGN),
//                "EUR", BigDecimal.valueOf(exchangeRateEur)));
//
//        this.currencyServiceToTest.refreshRates(exchangeRateDTO);
//
//        Optional<ExchangeRate> exRateEUR_BGN = this.exchangeRateRepository.findById("USD");
//
//        assertTrue(exRateEUR_BGN.isPresent());
//
//        assertEquals(
//                BigDecimal.valueOf(expectedValue).setScale(2, RoundingMode.DOWN),
//                exRateEUR_BGN.get().getExchangeRate());
//    }
//
//    private static Stream<Arguments> test_Data_BGN_TO_EUR() {
//        return Stream.of(
//                Arguments.of(1.84, 0.937, 0.54),
//                Arguments.of(1.77, 0.54, 0.56),
//                Arguments.of(1.0, 1.0, 1.0)
//        );
//    }


    private static Stream<Arguments> test_Data_BGN_TO_USD() {
        return Stream.of(
                Arguments.of(1.84, 0.54),
                Arguments.of(1.75, 0.57),
                Arguments.of(1.33, 0.75),
                Arguments.of(1.0, 1.0)
        );
    }

//    @Test
//    void test_BGN_TO_USD() {
//        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO("USD", Map.of("BGN", BigDecimal.valueOf(1.84)));
//
//        this.currencyServiceToTest.refreshRates(exchangeRateDTO);
//
//        Optional<ExchangeRate> exRateUSD_BGN = this.exchangeRateRepository.findById("USD");
//
//        assertTrue(exRateUSD_BGN.isPresent());
//
//        assertEquals(BigDecimal.valueOf(0.54), exRateUSD_BGN.get().getExchangeRate());
//    }
}