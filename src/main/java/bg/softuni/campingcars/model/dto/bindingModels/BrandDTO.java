package bg.softuni.campingcars.model.dto.bindingModels;

import java.util.Set;

public record BrandDTO(String name, Set<ModelDTO> models) {
}
