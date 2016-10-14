package org.traveling.spi.pricing;

import org.traveling.domain.LineOfBusiness;
import org.traveling.domain.traveler.Traveler;
import org.traveling.domain.traveler.Travelers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PriceOptimisationEngine<T extends LineOfBusiness> {

    Map<T, Map<Traveler, PriceOptimisation>> optimise(List<T> lobs, Travelers travelers, LocalDate departure);

}
