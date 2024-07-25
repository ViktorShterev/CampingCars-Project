package bg.softuni.campingcars.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "open.exchange.rates")
@Getter
public class OpenExchangeRateConfig {

    private String host;

    private String appId;

    private String schema;

    private List<String> symbols;

    private String path;

    private boolean enabled;


    public OpenExchangeRateConfig setPath(String path) {
        this.path = path;
        return this;
    }

    public OpenExchangeRateConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public OpenExchangeRateConfig setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public OpenExchangeRateConfig setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public OpenExchangeRateConfig setSymbols(List<String> symbols) {
        this.symbols = symbols;
        return this;
    }

    public OpenExchangeRateConfig setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public String toString() {
        return "OpenExchangeRateConfig{" +
                "host='" + host + '\'' +
                ", appId='" + appId + '\'' +
                ", schema='" + schema + '\'' +
                ", symbols=" + symbols +
                ", path='" + path + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
