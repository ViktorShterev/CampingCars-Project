package bg.softuni.campingcars.model.dto.bindingModels.brandRestDtos;

import java.util.Set;

public record BrandRestDTO(String name, Set<ModelRestDTO> models) {
}
