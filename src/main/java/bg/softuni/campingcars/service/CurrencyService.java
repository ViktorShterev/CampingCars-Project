package bg.softuni.campingcars.service;

import bg.softuni.campingcars.model.dto.bindingModels.ConvertRequestDTO;
import bg.softuni.campingcars.model.dto.bindingModels.ExchangeRateDTO;
import bg.softuni.campingcars.model.dto.bindingModels.MoneyDTO;

public interface CurrencyService {

    void refreshRates(ExchangeRateDTO exchangeRateDTO);

    MoneyDTO convert(ConvertRequestDTO convertRequestDTO);
}
