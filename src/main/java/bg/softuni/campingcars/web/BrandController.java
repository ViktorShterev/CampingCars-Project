package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.BrandDTO;
import bg.softuni.campingcars.model.dto.bindingModels.BrandModelAddBindingModel;
import bg.softuni.campingcars.model.enums.CategoryEnum;
import bg.softuni.campingcars.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping("/all")
    public ModelAndView getAllBrands() {
        List<BrandDTO> allBrands = this.brandService.getAllBrands();

        ModelAndView modelAndView = new ModelAndView("brands");
        modelAndView.addObject("brands", allBrands);

        return modelAndView;
    }

    @ModelAttribute("categories")
    public CategoryEnum[] categories() {
        return CategoryEnum.values();
    }

    @GetMapping("/add")
    public ModelAndView addBrandModel(@ModelAttribute("brandModelAddBindingModel") BrandModelAddBindingModel brandModelAddBindingModel) {

        return new ModelAndView("brand-model-add");
    }

    @PostMapping("/add")
    public ModelAndView addBrandModel(@ModelAttribute("brandModelAddBindingModel")
                                      @Valid BrandModelAddBindingModel brandModelAddBindingModel,
                                      BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("brand-model-add");
        }

        this.brandService.addBrandModel(brandModelAddBindingModel);

        return new ModelAndView("redirect:/");
    }
}
