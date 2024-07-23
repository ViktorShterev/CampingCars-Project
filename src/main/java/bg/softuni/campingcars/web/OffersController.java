package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.views.OfferViewModel;
import bg.softuni.campingcars.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OffersController {

    private final OfferService offerService;

    @GetMapping("/all")
    public ModelAndView offerCategory() {

        List<OfferViewModel> allOffers = this.offerService.findAllOffers();

        ModelAndView modelAndView = new ModelAndView("all-offers-categories");
        modelAndView.addObject("offers", allOffers);

        return modelAndView;
    }
}
