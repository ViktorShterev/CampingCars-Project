package bg.softuni.campingcars.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Table(name = "exchange_rates")
@Getter
public class ExchangeRate {

    @Id
    @NotNull
    private String currencyCode;

    @NotNull
    private BigDecimal exchangeRate;

    public ExchangeRate setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public ExchangeRate setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }
}
