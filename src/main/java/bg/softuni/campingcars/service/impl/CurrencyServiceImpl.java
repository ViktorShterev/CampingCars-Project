package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.ConvertRequestDTO;
import bg.softuni.campingcars.model.dto.bindingModels.ExchangeRateDTO;
import bg.softuni.campingcars.model.dto.bindingModels.MoneyDTO;
import bg.softuni.campingcars.model.entity.ExchangeRate;
import bg.softuni.campingcars.repository.ExchangeRateRepository;
import bg.softuni.campingcars.service.CurrencyService;
import bg.softuni.campingcars.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public void refreshRates(ExchangeRateDTO exchangeRateDTO) {

        LOGGER.info("Exchange rates received {}", exchangeRateDTO);

        BigDecimal BGN_TO_USD = getExchangeRate(exchangeRateDTO, "BGN", "USD")
                .orElse(null);

//        BigDecimal BGN_TO_EUR = getExchangeRate(exchangeRateDTO, "BGN", "EUR")
//                .orElse(null);

        if (BGN_TO_USD != null) {
            ExchangeRate exchangeRate = new ExchangeRate().setCurrencyCode("USD")
                    .setExchangeRate(BGN_TO_USD);

            this.exchangeRateRepository.save(exchangeRate);

        } else {
            LOGGER.error("Unable to exchange rate from BGN to USD");
        }

//        if (BGN_TO_EUR != null) {
//            ExchangeRate exchangeRate = new ExchangeRate().setCurrencyCode("EUR")
//                    .setExchangeRate(BGN_TO_EUR);
//
//            this.exchangeRateRepository.save(exchangeRate);
//
//        } else {
//            LOGGER.error("Unable to exchange rate from BGN to EUR");
//        }

        LOGGER.info("Rates refreshed...");
    }

    @Override
    public MoneyDTO convert(ConvertRequestDTO convertRequestDTO) {
        ExchangeRate exchangeRate = this.exchangeRateRepository.findById(convertRequestDTO.getTarget())
                .orElseThrow(() ->
                        new ObjectNotFoundException("Conversion to target " + convertRequestDTO.getTarget() + " not possible!"));

        return new MoneyDTO(convertRequestDTO.getTarget(),
                exchangeRate.getExchangeRate().multiply(BigDecimal.valueOf(convertRequestDTO.getAmount())));
    }

    private static Optional<BigDecimal> getExchangeRate(ExchangeRateDTO exchangeRateDTO,
                                                        String fromCurrency,
                                                        String toCurrency) {

        Objects.requireNonNull(fromCurrency, "From Currency cannot be null");
        Objects.requireNonNull(toCurrency, "To Currency cannot be null");

//        USD -> USD
        if (Objects.equals(fromCurrency, toCurrency)) {
            return Optional.of(BigDecimal.ONE);
        }

        if (fromCurrency.equals(exchangeRateDTO.base())) {
//            USD -> BGN
            if (exchangeRateDTO.rates().containsKey(toCurrency)) {
                return Optional.of(exchangeRateDTO.rates().get(toCurrency));
            }

        } else if (toCurrency.equals(exchangeRateDTO.base())) {
//            BGN -> USD
            if (exchangeRateDTO.rates().containsKey(fromCurrency)) {
                return Optional.of(BigDecimal.ONE.divide(
                        exchangeRateDTO.rates().get(fromCurrency),
                        3,
                        RoundingMode.DOWN
                ));
            }
        }

//        } else if (exchangeRateDTO.rates().containsKey(toCurrency)
//                && exchangeRateDTO.rates().containsKey(fromCurrency)) {
////            BGN -> EUR
//            return Optional.of(exchangeRateDTO.rates().get(toCurrency)
//                            .divide(exchangeRateDTO.rates().get(fromCurrency),
//                                    3,
//                                    RoundingMode.DOWN));
//        }

            return Optional.empty();
        }
    }
