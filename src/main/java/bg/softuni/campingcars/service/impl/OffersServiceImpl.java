package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.model.dto.views.OfferViewModel;
import bg.softuni.campingcars.model.entity.Offer;
import bg.softuni.campingcars.repository.OfferRepository;
import bg.softuni.campingcars.service.OffersService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OffersServiceImpl implements OffersService {

    private final OfferRepository offerRepository;

    @Override
    public Page<OfferViewModel> findAllOffers(Pageable pageable) {
        return this.offerRepository.findAll(pageable)
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
