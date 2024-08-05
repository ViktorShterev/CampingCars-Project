package bg.softuni.campingcars.web;

import bg.softuni.campingcars.model.dto.views.OfferViewModel;
import bg.softuni.campingcars.service.OffersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OffersController {

    private final OffersService offersService;

    @GetMapping("/all")
    public ModelAndView offerCategory(@PageableDefault(
                                            size = 3,
                                            sort = "uuid"
                                        ) Pageable pageable) {

        Page<OfferViewModel> allOffers = this.offersService.findAllOffers(pageable);

        ModelAndView modelAndView = new ModelAndView("all-offers-categories");
        modelAndView.addObject("offers", allOffers);

        return modelAndView;
    }
}
