package bg.softuni.campingcars.model.dto.bindingModels;

import java.math.BigDecimal;

public record MoneyDTO(String currency, BigDecimal amount) {
}
