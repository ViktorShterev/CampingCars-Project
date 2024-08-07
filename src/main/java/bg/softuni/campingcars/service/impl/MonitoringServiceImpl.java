package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.service.MonitoringService;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import io.micrometer.core.instrument.Counter;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    private final Logger LOGGER = LoggerFactory.getLogger(MonitoringServiceImpl.class);

    private final Counter offerSearches;

    public MonitoringServiceImpl(MeterRegistry meterRegistry) {
        this.offerSearches = Counter
                .builder("offer_search_cnt")
                .description("How many offer searches we have performed")
                .register(meterRegistry);
    }

    @Override
    public void logOfferSearch() {

        LOGGER.info("Offer search was performed.");

        this.offerSearches.increment();
    }
}
