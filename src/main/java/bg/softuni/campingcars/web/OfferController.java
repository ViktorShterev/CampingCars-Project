package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.bindingModels.BrandDTO;
import bg.softuni.campingcars.model.dto.bindingModels.OfferSummaryDTO;
import bg.softuni.campingcars.model.dto.bindingModels.UpdateOfferBindingModel;
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

import java.util.List;
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

    @ModelAttribute("brands")
    public List<BrandDTO> brands() {
        return this.brandService.getAllBrands();
    }

    @ModelAttribute("engines")
    public EngineEnum[] engines() {
        return EngineEnum.values();
    }

    @ModelAttribute("transmissions")
    public TransmissionEnum[] transmissions() {
        return TransmissionEnum.values();
    }

    @GetMapping("/add/camper")
    public ModelAndView addCamperOffer(@ModelAttribute("offerAddCamperBindingModel") OfferAddCamperBindingModel offerAddCamperBindingModel) {

        return new ModelAndView("offer-add-camper");
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

        return new ModelAndView("redirect:/offer/details/" + offerUUID);
    }

    @GetMapping("/add/caravan")
    public ModelAndView addCaravanOffer(@ModelAttribute("offerAddCaravanBindingModel") OfferAddCaravanBindingModel offerAddCaravanBindingModel) {

        return new ModelAndView("offer-add-caravan");
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

        return new ModelAndView("redirect:/offer/details/" + offerUUID);
    }

    @GetMapping("/details/{uuid}")
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
    public ModelAndView delete(@PathVariable("uuid") UUID uuid,
                               @AuthenticationPrincipal UserDetails principal) {

        this.offerService.deleteOffer(uuid, principal);

        return new ModelAndView("redirect:/offers/all");
    }

    @PreAuthorize("@offerServiceImpl.isOwner(#uuid, #viewer.username)")
    @GetMapping("/update/{uuid}")
    public ModelAndView update(@PathVariable("uuid") UUID uuid,
                               @AuthenticationPrincipal UserDetails viewer) {

        ModelAndView modelAndView = new ModelAndView("update");

        UpdateOfferBindingModel updateOfferBindingModel = this.offerService.getOfferForUpdate(uuid, viewer);

        if (updateOfferBindingModel != null) {
            modelAndView.addObject("updateOfferBindingModel", updateOfferBindingModel);

            return modelAndView;
        }

        return new ModelAndView("redirect:/offer/details/" + uuid);
    }

    @PreAuthorize("@offerServiceImpl.isOwner(#uuid, #principal.username)")
    @PutMapping("/update/{uuid}")
    public ModelAndView update(@PathVariable("uuid") UUID uuid,
                               @ModelAttribute("updateOfferBindingModel")
                               @Valid UpdateOfferBindingModel updateOfferBindingModel,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails principal) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("redirect:/offer/update/" + uuid);
        }

        this.offerService.updateOffer(uuid, updateOfferBindingModel, principal);

        return new ModelAndView("redirect:/offer/details/" + uuid);
    }
}
