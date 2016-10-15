package org.traveling.lob.flights.pricing;

import org.traveling.domain.traveler.Traveler;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.spi.pricing.PriceOptimisation;
import org.traveling.spi.pricing.PriceRule;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DaysDeparturePriceRule implements PriceRule<Flight> {

    @Override
    public PriceOptimisation apply(PriceOptimisation currentPriceOptimisation, Flight lob, Traveler traveler, LocalDate departure) {

        if (traveler.getTravelerType().isInfant()) return currentPriceOptimisation;

        long daysPriorToDeparture = ChronoUnit.DAYS.between(LocalDate.now(), departure);
        Integer percentageToApply = percentageToApplyForDays((int) daysPriorToDeparture);
        Float optimisedPrice = currentPriceOptimisation.getOptimised() * percentageToApply / 100;

        return currentPriceOptimisation.update(optimisedPrice, percentageToApply + "%");

    }

    private Integer percentageToApplyForDays(Integer days) {
        if (isBetween(days, 0, 2)) return 150;
        if (isBetween(days, 3, 15)) return 120;
        if (isBetween(days, 16, 30)) return 100;
        return 80;
    }

    private boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

}
