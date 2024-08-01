package bg.softuni.campingcars.init;

import bg.softuni.campingcars.config.OpenExchangeRateConfig;
import bg.softuni.campingcars.service.util.RefreshingRatesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatesInit implements CommandLineRunner {

    private final OpenExchangeRateConfig openExchangeRateConfig;

    private final RefreshingRatesUtil refreshingRatesUtil;


    @Override
    public void run(String... args) throws Exception {

        if (openExchangeRateConfig.isEnabled()) {

            this.refreshingRatesUtil.refreshRates();
        }
    }
}
