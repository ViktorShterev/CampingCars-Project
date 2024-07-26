package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.BrandDTO;
import bg.softuni.campingcars.model.dto.bindingModels.BrandModelAddBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.ModelDTO;
import bg.softuni.campingcars.model.entity.Brand;
import bg.softuni.campingcars.model.entity.Category;
import bg.softuni.campingcars.model.entity.Model;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import bg.softuni.campingcars.repository.BrandRepository;
import bg.softuni.campingcars.repository.CategoryRepository;
import bg.softuni.campingcars.repository.ModelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBrands() {
        // Arrange
        Category category = new Category().setCategory(CategoryEnum.CAMPER);

        Brand brand = new Brand();
        brand.setName("Brand1");

        Model model = new Model();
        model.setName("Model1");
        model.setCategory(category);
        model.setBrand(brand);

        brand.setModels(Set.of(model));

        this.brandRepository.save(brand);
        this.modelRepository.save(model);

        when(brandRepository.findAll()).thenReturn(List.of(brand));

        // Act
        List<BrandDTO> result = brandService.getAllBrands();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        BrandDTO brandDTO = result.get(0);
        assertEquals("Brand1", brandDTO.name());
        assertEquals(1, brandDTO.models().size());
        ModelDTO modelDTO = brandDTO.models().iterator().next();
        assertEquals(1L, modelDTO.id());
        assertEquals("Model1", modelDTO.name());
        assertEquals("CAMPER", modelDTO.category());
    }

    @Test
    void testAddBrandModelWhenBrandExists() {
        // Arrange
        String brandName = "ExistingBrand";
        String modelName = "NewModel";
        CategoryEnum categoryEnum = CategoryEnum.CAMPER;

        Brand existingBrand = new Brand();
        existingBrand.setName(brandName);

        BrandModelAddBindingModel bindingModel = new BrandModelAddBindingModel(brandName, modelName, categoryEnum);

        Category category = new Category().setCategory(categoryEnum);

        when(brandRepository.findByName(brandName)).thenReturn(Optional.of(existingBrand));
        when(categoryRepository.findByCategory(categoryEnum)).thenReturn(Optional.of(category));

        // Act
        brandService.addBrandModel(bindingModel);

        // Assert
        verify(modelRepository).save(any(Model.class));
        verify(brandRepository).save(existingBrand);
    }

    @Test
    void testAddBrandModelWhenBrandDoesNotExist() {
        // Arrange
        String newBrandName = "NewBrand";
        String modelName = "NewModel";
        CategoryEnum categoryEnum = CategoryEnum.CAMPER;

        BrandModelAddBindingModel bindingModel = new BrandModelAddBindingModel(newBrandName, modelName, categoryEnum);

        Category category = new Category().setCategory(categoryEnum);

        when(brandRepository.findByName(newBrandName)).thenReturn(Optional.empty());
        when(categoryRepository.findByCategory(categoryEnum)).thenReturn(Optional.of(category));

        // Act
        brandService.addBrandModel(bindingModel);

        // Assert
        verify(modelRepository).save(any(Model.class));
        verify(brandRepository).save(any(Brand.class));
    }
}