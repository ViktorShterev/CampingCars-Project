package bg.softuni.campingcars.service.schedulers;

import bg.softuni.campingcars.service.util.RefreshingRatesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyRefreshRatesScheduler {

    private final RefreshingRatesUtil refreshingRatesUtil;

    @Scheduled(cron = "0 0 7 * * *")
    public void refreshingRates() {

        this.refreshingRatesUtil.refreshRates();
    }
}
