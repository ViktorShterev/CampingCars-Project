package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCamperBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCaravanBindingModel;
import bg.softuni.campingcars.model.enums.EngineEnum;
import bg.softuni.campingcars.model.enums.TransmissionEnum;
import bg.softuni.campingcars.service.BrandService;
import bg.softuni.campingcars.service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;

    @GetMapping("/category")
    public ModelAndView offerCategory() {
        return new ModelAndView("offer-category");
    }

    @GetMapping("/add/camper")
    public ModelAndView addCamperOffer(@ModelAttribute("offerAddCamperBindingModel") OfferAddCamperBindingModel offerAddCamperBindingModel) {

        ModelAndView modelAndView = new ModelAndView("offer-add-camper");

        modelAndView.addObject("brands", this.brandService.getAllBrands());
        modelAndView.addObject("engines", EngineEnum.values());
        modelAndView.addObject("transmissions", TransmissionEnum.values());

        return modelAndView;
    }

    @PostMapping("/add/camper")
    public ModelAndView addCamperOffer(@ModelAttribute("offerAddCamperBindingModel")
                                 @Valid OfferAddCamperBindingModel offerAddCamperBindingModel,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("offer-add-camper");
        }

        this.offerService.addCamperOffer(offerAddCamperBindingModel);

        return new ModelAndView("redirect:/offers");
    }

    @GetMapping("/add/caravan")
    public ModelAndView addCaravanOffer(@ModelAttribute("offerAddCaravanBindingModel") OfferAddCaravanBindingModel offerAddCaravanBindingModel) {

        ModelAndView modelAndView = new ModelAndView("offer-add-caravan");

        modelAndView.addObject("brands", this.brandService.getAllBrands());

        return modelAndView;
    }

    @PostMapping("/add/caravan")
    public ModelAndView addCaravanOffer(@ModelAttribute("offerAddCaravanBindingModel")
                                 @Valid OfferAddCaravanBindingModel offerAddCaravanBindingModel,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("offer-add-caravan");
        }

        this.offerService.addCaravanOffer(offerAddCaravanBindingModel);

        return new ModelAndView("redirect:/offers");
    }
}
