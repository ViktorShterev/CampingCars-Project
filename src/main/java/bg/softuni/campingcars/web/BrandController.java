package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.BrandDTO;
import bg.softuni.campingcars.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/brand")
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
}
