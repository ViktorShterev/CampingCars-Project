package bg.softuni.campingcars.service.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* bg.softuni.campingcars.service.OffersService.findAllOffers(..))")
    public void trackOfferSearch() {}

    @Pointcut("@annotation(WarnIfExecutionExceeds)")
    public void warnIfExecutionExceeds() {}
}
