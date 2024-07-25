package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.BrandDTO;
import bg.softuni.campingcars.model.dto.bindingModels.BrandModelAddBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.ModelDTO;
import bg.softuni.campingcars.model.entity.Brand;
import bg.softuni.campingcars.model.entity.Category;
import bg.softuni.campingcars.model.entity.Model;
import bg.softuni.campingcars.repository.BrandRepository;
import bg.softuni.campingcars.repository.CategoryRepository;
import bg.softuni.campingcars.repository.ModelRepository;
import bg.softuni.campingcars.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<BrandDTO> getAllBrands() {
        List<Brand> all = this.brandRepository.findAll();

        List<BrandDTO> brandDTOS = new ArrayList<>();

        for (Brand brand : all) {

            Set<ModelDTO> modelDTOS = new HashSet<>();

            brand.getModels()
                    .forEach(model -> {
                        ModelDTO modelDTO = new ModelDTO(model.getId(), model.getName(), model.getCategory().getCategory().name());

                        modelDTOS.add(modelDTO);
                    });

            BrandDTO brandDTO = new BrandDTO(brand.getName(), modelDTOS);
            brandDTOS.add(brandDTO);
        }

        return brandDTOS;
    }

    @Override
    public void addBrandModel(BrandModelAddBindingModel brandModelAddBindingModel) {

        if (brandModelAddBindingModel != null) {

            Optional<Brand> brandOptional = this.brandRepository.findByName(brandModelAddBindingModel.brand());

            if (brandOptional.isPresent()) {
                Brand brand = brandOptional.get();

                updatingBrand(brandModelAddBindingModel, brand);

            } else {
                Brand brand = new Brand()
                        .setName(brandModelAddBindingModel.brand());

                updatingBrand(brandModelAddBindingModel, brand);
            }
        }
    }

    private void updatingBrand(BrandModelAddBindingModel brandModelAddBindingModel, Brand brand) {
        Category category = this.categoryRepository.findByCategory(brandModelAddBindingModel.category())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Model model = new Model()
                .setBrand(brand)
                .setName(brandModelAddBindingModel.model())
                .setCategory(category);

        this.modelRepository.save(model);

        brand.getModels().add(model);

        this.brandRepository.save(brand);
    }
}
