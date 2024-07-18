package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.repository.OfferRepository;
import bg.softuni.campingcars.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;


}
