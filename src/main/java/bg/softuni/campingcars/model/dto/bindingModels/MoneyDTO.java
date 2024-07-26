package bg.softuni.campingcars.model.dto.bindingModels;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public  class MoneyDTO {

    private String currency;
    private BigDecimal amount;

    public MoneyDTO(String currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }
}
