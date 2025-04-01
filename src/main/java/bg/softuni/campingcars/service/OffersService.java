package bg.softuni.campingcars.service;

import bg.softuni.campingcars.model.dto.views.OfferViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OffersService {

    Page<OfferViewModel> findAllOffers(Pageable pageable);

    Page<OfferViewModel> findUserOffers(Pageable pageable);
}
