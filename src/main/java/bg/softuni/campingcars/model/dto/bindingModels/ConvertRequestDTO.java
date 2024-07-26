package bg.softuni.campingcars.model.dto.bindingModels;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConvertRequestDTO {

    @NotEmpty
    private String target;

    @NotNull
    @Positive
    private Double amount;
}
