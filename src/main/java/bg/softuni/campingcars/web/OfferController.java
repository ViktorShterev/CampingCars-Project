package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.OfferSummaryDTO;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCamperBindingModel;
import bg.softuni.campingcars.model.dto.bindingModels.offers.OfferAddCaravanBindingModel;
import bg.softuni.campingcars.model.enums.EngineEnum;
import bg.softuni.campingcars.model.enums.TransmissionEnum;
import bg.softuni.campingcars.service.BrandService;
import bg.softuni.campingcars.service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/offer")
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
                                       BindingResult bindingResult,
                                       @AuthenticationPrincipal UserDetails seller) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("offer-add-camper");
        }

        UUID offerUUID = this.offerService.addCamperOffer(offerAddCamperBindingModel, seller);

        return new ModelAndView("redirect:/offer/" + offerUUID);
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
                                        BindingResult bindingResult,
                                        @AuthenticationPrincipal UserDetails seller) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("offer-add-caravan");
        }

        UUID offerUUID = this.offerService.addCaravanOffer(offerAddCaravanBindingModel, seller);

        return new ModelAndView("redirect:/offer/" + offerUUID);
    }

    @GetMapping("/{uuid}")
    public ModelAndView details(@PathVariable("uuid") UUID uuid,
                          @AuthenticationPrincipal UserDetails viewer) {

        OfferSummaryDTO offerSummaryDTO = this.offerService.getOfferDetail(uuid, viewer)
                .orElseThrow(() -> new IllegalArgumentException("Offer with uuid " + uuid + " was not found!"));

        ModelAndView modelAndView = new ModelAndView("details");
        modelAndView.addObject("offer", offerSummaryDTO);

        return modelAndView;
    }

    @PreAuthorize("@offerServiceImpl.isOwner(#uuid, #principal.username)")
    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") UUID uuid,
                         @AuthenticationPrincipal UserDetails principal) {

        this.offerService.deleteOffer(uuid);

        return "redirect:/offers/all";
    }
}
