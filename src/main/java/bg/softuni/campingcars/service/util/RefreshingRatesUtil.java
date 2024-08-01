package bg.softuni.campingcars.service.util;

import bg.softuni.campingcars.config.OpenExchangeRateConfig;
import bg.softuni.campingcars.model.dto.bindingModels.ExchangeRateDTO;
import bg.softuni.campingcars.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Component
@RequiredArgsConstructor
public class RefreshingRatesUtil {

    private final CurrencyService currencyService;

    private final OpenExchangeRateConfig openExchangeRateConfig;

    private final RestTemplate restTemplate;

    public void refreshRates() {
        String openExchangeRatesUrlTemplate =
                this.openExchangeRateConfig.getSchema() +
                        "://" +
                        this.openExchangeRateConfig.getHost() +
                        this.openExchangeRateConfig.getPath() +
                        "?app_id={app_id}&symbols={symbols}";

        Map<String, String> requestParams = Map.of(
                "app_id", this.openExchangeRateConfig.getAppId(),
                "symbols", String.join(",", this.openExchangeRateConfig.getSymbols())
        );

        ExchangeRateDTO exchangeRateDTO = this.restTemplate
                .getForObject(openExchangeRatesUrlTemplate, ExchangeRateDTO.class, requestParams);

        this.currencyService.refreshRates(exchangeRateDTO);
    }
}
