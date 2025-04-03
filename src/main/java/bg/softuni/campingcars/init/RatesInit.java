package bg.softuni.campingcars.init;

import bg.softuni.campingcars.repository.ExchangeRateRepository;
import bg.softuni.campingcars.service.util.RefreshingRatesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatesInit implements CommandLineRunner {

    private final ExchangeRateRepository exchangeRateRepository;
    private final RefreshingRatesUtil refreshingRatesUtil;

    @Override
    public void run(String... args) throws Exception {

        if (exchangeRateRepository.count() == 0) {
            this.refreshingRatesUtil.refreshRates();
        }
    }
}
