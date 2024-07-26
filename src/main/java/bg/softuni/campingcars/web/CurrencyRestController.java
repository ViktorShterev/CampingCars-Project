package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.ConvertRequestDTO;
import bg.softuni.campingcars.model.dto.bindingModels.MoneyDTO;
import bg.softuni.campingcars.service.CurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyRestController {

    private final CurrencyService currencyService;

    @Operation(summary = "Converts BGN to a target currency.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returned when we successfully converted the currency.",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MoneyDTO.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "There is no information about this currency.",
                    content = @Content
            )
    })
    @Parameter(name = "target", description = "The target currency", required = true)
    @Parameter(name = "amount", description = "The amount to be converted", required = true)
    @GetMapping("/api/currency/convert")
    public ResponseEntity<Map<String, Object>> convertToUSD(
            @RequestParam("target") String target,
            @RequestParam("amount") double amount) {

        ConvertRequestDTO convertRequestDTO = new ConvertRequestDTO();
        convertRequestDTO.setTarget(target);
        convertRequestDTO.setAmount(amount);

        MoneyDTO moneyDTO = this.currencyService.convert(convertRequestDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("convertedAmount", moneyDTO.getAmount());

        return ResponseEntity.ok(response);
    }
}
