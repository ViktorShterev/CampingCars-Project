package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.BrandDTO;
import bg.softuni.campingcars.model.dto.bindingModels.BrandModelAddBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.ModelDTO;
import bg.softuni.campingcars.model.dto.bindingModels.brandRestDtos.BrandRestDTO;
import bg.softuni.campingcars.model.dto.bindingModels.brandRestDtos.ModelRestDTO;
import bg.softuni.campingcars.model.entity.Category;
import bg.softuni.campingcars.model.entity.Model;
import bg.softuni.campingcars.repository.CategoryRepository;
import bg.softuni.campingcars.repository.ModelRepository;
import bg.softuni.campingcars.repository.OfferRepository;
import bg.softuni.campingcars.service.BrandService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final ModelRepository modelRepository;
    private final CategoryRepository categoryRepository;
    private final OfferRepository offerRepository;
    private final RestClient restClient;

    @Override
    public List<BrandDTO> getAllBrands() {

        List<BrandRestDTO> restDTO = this.restClient
                .get()
                .uri("http://brands-microservice:8081/brands/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        List<BrandDTO> brandDTOS = new ArrayList<>();

        if (restDTO != null) {
            for (BrandRestDTO brandRestDTO : restDTO) {

                String brandName = brandRestDTO.name();

                List<Model> models = this.modelRepository.findAllByBrandName(brandName);

                Set<ModelDTO> modelDTOS = new HashSet<>();

                for (Model model : models) {
                    ModelDTO modelDTO = new ModelDTO(model.getId(), model.getName(), model.getCategory().getCategory().name());

                    modelDTOS.add(modelDTO);
                }

                BrandDTO brandDTO = new BrandDTO(brandName, modelDTOS);
                brandDTOS.add(brandDTO);
            }

            return brandDTOS;
        }

        return brandDTOS;
    }

    @Override
    public void addBrandModel(BrandModelAddBindingModel brandModelAddBindingModel) {

        if (brandModelAddBindingModel != null) {

            Category category = this.categoryRepository.findByCategory(brandModelAddBindingModel.category()).get();
            Model model = new Model();

            model.setName(brandModelAddBindingModel.model());
            model.setCategory(category);
            model.setBrandName(brandModelAddBindingModel.brand());

            this.modelRepository.save(model);

            BrandRestDTO restDTO = new BrandRestDTO(brandModelAddBindingModel.brand(), Set.of(new ModelRestDTO(model.getId())));

            this.restClient
                    .post()
                    .uri("http://brands-microservice:8081/brands/add")
                    .body(restDTO)
                    .retrieve();

        }
    }

    @Override
    @Transactional
    public void deleteOffer(String name, UserDetails principal) {
        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {

            List<Model> models = this.modelRepository.findAllByBrandName(name);

            List<Long> modelIds = new ArrayList<>();

            for (Model model : models) {
                modelIds.add(model.getId());
            }

            if (this.offerRepository.findAllByModelIdIn(modelIds).isEmpty()) {

                this.modelRepository.deleteAllByBrandName(name);

                this.restClient
                        .delete()
                        .uri("http://brands-microservice:8081/brands/{name}", name)
                        .retrieve()
                        .toBodilessEntity();
            }
        }
    }
}
