//package bg.softuni.campingcars.service.impl;
//
//import bg.softuni.campingcars.model.dto.bindingModels.BrandDTO;
//import bg.softuni.campingcars.model.dto.bindingModels.BrandModelAddBindingModel;
//import bg.softuni.campingcars.model.enums.CategoryEnum;
//import bg.softuni.campingcars.repository.CategoryRepository;
//import bg.softuni.campingcars.repository.ModelRepository;
//import bg.softuni.campingcars.service.BrandService;
//import bg.softuni.campingcars.testUtils.TestDataUtil;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class BrandServiceImplTest {
//
//    @Autowired
//    private BrandRepository brandRepository;
//
//    @Autowired
//    private TestDataUtil testDataUtil;
//
//    @Autowired
//    private BrandService brandService;
//
//    @Autowired
//    private ModelRepository modelRepository;
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @BeforeEach
//    void setUp() {
//        this.testDataUtil.cleanUp();
//    }
//
//    @AfterEach
//    void tearDown() {
//        this.testDataUtil.cleanUp();
//    }
//
//    @Test
//    void testBrandRepositoryReturnEmptyList() {
//
//        List<BrandDTO> allBrands = this.brandService.getAllBrands();
//
//        assertEquals(0, allBrands.size());
//    }
//
//    @Test
//    void testBrandRepositoryReturns() {
//
//        this.testDataUtil.creatingTestBrand();
//
//        List<BrandDTO> allBrands = this.brandService.getAllBrands();
//
//        assertEquals(1, allBrands.size());
//        assertEquals("Test Brand", allBrands.get(0).name());
//        assertEquals(1, allBrands.get(0).models().size());
//        assertEquals("Test Model", allBrands.get(0).models().stream().findFirst().get().name());
//        assertEquals("CAMPER", allBrands.get(0).models().stream().findFirst().get().category());
//    }
//
//    @Test
//    void testBrandAdd() {
//
//        BrandModelAddBindingModel brandModelAddBindingModel = new BrandModelAddBindingModel("New Brand", "New Model", CategoryEnum.CARAVAN);
//
//        this.brandService.addBrandModel(brandModelAddBindingModel);
//
//        assertTrue(this.brandRepository.findByName("New Brand").isPresent());
//        assertTrue(this.modelRepository.findByName("New Model").isPresent());
//    }
//
//    @Test
//    void testBrandUpdate() {
//        Brand brand = this.testDataUtil.creatingTestBrand();
//
//        BrandModelAddBindingModel brandModelAddBindingModel = new BrandModelAddBindingModel("Test Brand", "New Model", CategoryEnum.CARAVAN);
//
//        assertTrue(this.brandRepository.findByName(brand.getName()).isPresent());
//        assertTrue(this.modelRepository.findByName(brand.getModels().iterator().next().getName()).isPresent());
//
//        assertEquals("Test Brand", brand.getName());
//        assertEquals("Test Model", brand.getModels().iterator().next().getName());
//        assertEquals("CAMPER", brand.getModels().iterator().next().getCategory().getCategory().name());
//
//        this.brandService.addBrandModel(brandModelAddBindingModel);
//
//        Brand updatedBrand = this.brandRepository.findByName(brand.getName()).get();
//
//        assertEquals(2, updatedBrand.getModels().size());
//        assertEquals("New Model", updatedBrand.getModels().stream().skip(1).findFirst().get().getName());
//        assertEquals("CARAVAN", updatedBrand.getModels().stream().skip(1).findFirst().get().getCategory().getCategory().name());
//
//        assertTrue(this.modelRepository.findByName(updatedBrand.getModels().stream().skip(1).findFirst().get().getName()).isPresent());
//    }
//
//    @Test
//    void testBrandUpdateThrowsInvalidCategory() {
//        Brand brand = this.testDataUtil.creatingTestBrand();
//
//        BrandModelAddBindingModel brandModelAddBindingModel = new BrandModelAddBindingModel("Test Brand", "New Model", null);
//
//        assertTrue(this.brandRepository.findByName(brand.getName()).isPresent());
//        assertTrue(this.modelRepository.findByName(brand.getModels().iterator().next().getName()).isPresent());
//
//        assertEquals("Test Brand", brand.getName());
//        assertEquals("Test Model", brand.getModels().iterator().next().getName());
//        assertEquals("CAMPER", brand.getModels().iterator().next().getCategory().getCategory().name());
//
//        RuntimeException exception = assertThrows(
//                RuntimeException.class,
//                () -> brandService.addBrandModel(brandModelAddBindingModel)
//        );
//
//        assertEquals("Category not found", exception.getMessage());
//    }
//}