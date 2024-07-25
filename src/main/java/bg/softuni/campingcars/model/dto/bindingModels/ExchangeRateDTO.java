package bg.softuni.campingcars.model.dto.bindingModels;

//{
//        "disclaimer": "Usage subject to terms: https://openexchangerates.org/terms",
//        "license": "https://openexchangerates.org/license",
//        "timestamp": 1717585200,
//        "base": "USD",
//        "rates": {
//        "BGN": 1.797083,
//        "EUR": 0.920083
//        }
//        }

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRateDTO(String base, Map<String, BigDecimal> rates) {

}
