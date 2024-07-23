package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.BrandDTO;
import bg.softuni.campingcars.model.dto.bindingModels.ModelDTO;
import bg.softuni.campingcars.model.entity.Brand;
import bg.softuni.campingcars.repository.BrandRepository;
import bg.softuni.campingcars.repository.ModelRepository;
import bg.softuni.campingcars.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<BrandDTO> getAllBrands() {
        List<Brand> all = this.brandRepository.findAll();

        List<BrandDTO> brandDTOS = new ArrayList<>();

        for (Brand brand : all) {

            Set<ModelDTO> modelDTOS = new HashSet<>();

            brand.getModels()
                    .forEach(model -> {
                        ModelDTO modelDTO = new ModelDTO(model.getId(), model.getName(), model.getCategory().getId());

                        modelDTOS.add(modelDTO);
                    });

            BrandDTO brandDTO = new BrandDTO(brand.getName(), modelDTOS);
            brandDTOS.add(brandDTO);
        }

        return brandDTOS;
    }
}
