package org.traveling.spi.pricing;

import org.traveling.domain.LineOfBusiness;
import org.traveling.domain.traveler.Traveler;

import java.time.LocalDate;

public interface PriceRule<T extends LineOfBusiness> {

    PriceOptimisation apply(final PriceOptimisation currentPriceOptimisation, final T lob, Traveler traveler, final LocalDate departure);

}
