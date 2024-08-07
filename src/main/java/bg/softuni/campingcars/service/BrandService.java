package bg.softuni.campingcars.service;

import bg.softuni.campingcars.model.dto.bindingModels.BrandDTO;
import bg.softuni.campingcars.model.dto.bindingModels.BrandModelAddBindingModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface BrandService {

    List<BrandDTO> getAllBrands();

    void addBrandModel(BrandModelAddBindingModel brandModelAddBindingModel);

    void deleteOffer(String name, UserDetails principal);
}
