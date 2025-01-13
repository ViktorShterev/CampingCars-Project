package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.bindingModels.BrandDTO;
import bg.softuni.campingcars.model.dto.bindingModels.BrandModelAddBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.brandRestDtos.BrandRestDTO;
import bg.softuni.campingcars.model.dto.bindingModels.brandRestDtos.ModelRestDTO;
import bg.softuni.campingcars.model.entity.Category;
import bg.softuni.campingcars.model.entity.Model;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import bg.softuni.campingcars.repository.CategoryRepository;
import bg.softuni.campingcars.repository.ModelRepository;
import bg.softuni.campingcars.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BrandServiceImplTest {

    private BrandServiceImpl toTest;

    @Mock
    private ModelRepository mockModelRepository;

    @Mock
    private OfferRepository mockOfferRepository;

    @Mock
    private CategoryRepository mockCategoryRepository;

    @Mock
    private RestClient mockRestClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @Mock
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RestClient.RequestBodySpec requestBodySpec;

    @Mock
    private UserDetails principal;

    @BeforeEach
    void setUp() {
        toTest = new BrandServiceImpl(mockModelRepository, mockCategoryRepository, mockOfferRepository, mockRestClient);
    }

    @Test
    void testGetAllBrandsReturnsValue() {
        BrandRestDTO brandRestDTO = createBrandRestDTO();

        when(mockRestClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/brands/all")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(new ParameterizedTypeReference<List<BrandRestDTO>>() {
        })).thenReturn(List.of(brandRestDTO));

//        when(mockRestClient.get()
//                .uri("/brands/all")
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .body(new ParameterizedTypeReference<>() {
//                }))
//                .thenReturn(brandRestDTO);

        when(mockModelRepository.findAllByBrandName(brandRestDTO.name()))
                .thenReturn(List.of(new Model().setBrandName("test").setName("Cool").setCategory(new Category().setCategory(CategoryEnum.CAMPER))));

        List<BrandDTO> allBrands = toTest.getAllBrands();
        assertNotNull(allBrands);
        assertFalse(allBrands.isEmpty());
        assertEquals(1, allBrands.size());
    }

    @Test
    void testGetAllBrandsReturnsEmpty() {

        when(mockRestClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/brands/all")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(new ParameterizedTypeReference<List<BrandRestDTO>>() {
        })).thenReturn(List.of());

//        when(mockRestClient.get()
//                .uri("/brands/all")
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .body(new ParameterizedTypeReference<>() {
//                }))
//                .thenReturn(brandRestDTO);

        List<BrandDTO> allBrands = toTest.getAllBrands();
        assertNotNull(allBrands);
        assertTrue(allBrands.isEmpty());
    }

    @Test
    void testAddBrandModelWorks() {
        BrandModelAddBindingModel bindingModel = new BrandModelAddBindingModel("BrandX", "ModelY", CategoryEnum.CAMPER);

        Category category = new Category().setCategory(CategoryEnum.CAMPER);

        Model savedModel = (Model) new Model().setBrandName("test").setName("Cool").setCategory(new Category().setCategory(CategoryEnum.CAMPER)).setId(1L);

        when(mockCategoryRepository.findByCategory(CategoryEnum.CAMPER)).thenReturn(Optional.of(category));
        when(mockModelRepository.save(any(Model.class))).thenReturn(savedModel);

        when(mockRestClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri("/brands/add")).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any(BrandRestDTO.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(null); // Simulate successful request

        toTest.addBrandModel(bindingModel);

        verify(mockCategoryRepository, times(1)).findByCategory(CategoryEnum.CAMPER);
        verify(mockModelRepository, times(1)).save(any(Model.class));
        verify(mockRestClient, times(1)).post();
        verify(requestBodyUriSpec, times(1)).uri("/brands/add");
        verify(requestBodySpec, times(1)).body(any(BrandRestDTO.class));
    }


    @Test
    void testDeleteOfferShouldDeleteWhenAdmin() {

        String brandName = "test";
        UserDetails principal = new User("username", "password", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        Model model = new Model();
        model.setId(1L);
        when(mockModelRepository.findAllByBrandName(brandName)).thenReturn(List.of(model));

        when(mockOfferRepository.findAllByModelIdIn(any())).thenReturn(List.of());

        when(mockRestClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(mock(RestClient.ResponseSpec.class));

        // Act
        toTest.deleteOffer(brandName, principal);

        // Assert
        verify(mockModelRepository, times(1)).findAllByBrandName(brandName);
        verify(mockOfferRepository, times(1)).findAllByModelIdIn(any());
        verify(mockModelRepository, times(1)).deleteAllByBrandName(brandName);

        // Verify RestClient DELETE interaction
        verify(mockRestClient, times(1)).delete();
        verify(requestHeadersUriSpec, times(1)).uri(any(Function.class));
        verify(requestHeadersSpec, times(1)).retrieve();
    }

    @Test
    void deleteOffer_ShouldNotDeleteOrCallRestClient_WhenNotAdmin() {

        String brandName = "test";

        UserDetails principal = new User("username", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        toTest.deleteOffer(brandName, principal);

        verify(mockModelRepository, never()).findAllByBrandName(brandName);
        verify(mockOfferRepository, never()).findAllByModelIdIn(any());
        verify(mockModelRepository, never()).deleteAllByBrandName(any());
        verify(mockRestClient, never()).delete();
    }

    private BrandRestDTO createBrandRestDTO() {
        return new BrandRestDTO("test", Set.of(new ModelRestDTO(1L)));
    }
}