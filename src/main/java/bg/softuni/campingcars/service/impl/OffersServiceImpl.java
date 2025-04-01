package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.views.OfferViewModel;
import bg.softuni.campingcars.model.entity.Offer;
import bg.softuni.campingcars.model.user.CampingCarsUserDetails;
import bg.softuni.campingcars.repository.OfferRepository;
import bg.softuni.campingcars.service.AuthenticationService;
import bg.softuni.campingcars.service.OffersService;
import bg.softuni.campingcars.service.aop.WarnIfExecutionExceeds;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OffersServiceImpl implements OffersService {

    private final OfferRepository offerRepository;
    private final AuthenticationService authenticationService;

    @WarnIfExecutionExceeds(timeInMillis = 1000L)
    @Override
    public Page<OfferViewModel> findAllOffers(Pageable pageable) {
        return this.offerRepository.findAll(pageable)
                .map(this::mapped);
    }

    @Override
    public Page<OfferViewModel> findUserOffers(Pageable pageable) {

        CampingCarsUserDetails campingCarsUserDetails = this.authenticationService.getCurrentUser().get();

        return this.offerRepository.findAllBySeller_Email(campingCarsUserDetails.getUsername(), pageable)
                .map(this::mapped);
    }

    private OfferViewModel mapped(Offer offer) {
        return new OfferViewModel(
                offer.getUuid().toString(),
                offer.getModel().getBrandName(),
                offer.getModel().getName(),
                offer.getCategory().getCategory().name(),
                offer.getYear(),
                offer.getPrice(),
                offer.getImageUrl()
        );
    }
}
