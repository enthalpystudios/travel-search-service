package org.traveling.lob.flights.pricing;

import org.traveling.domain.traveler.Traveler;
import org.traveling.lob.flights.domain.Airline;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.spi.pricing.PriceOptimisation;
import org.traveling.spi.pricing.PriceRule;

import java.time.LocalDate;
import java.util.Map;

public class PassengerTypePriceRule implements PriceRule<Flight> {

    private final Map<Airline, Float> airlineInfantPricesDataSet;

    public PassengerTypePriceRule(Map<Airline, Float> airlineInfantPricesDataSet) {
        this.airlineInfantPricesDataSet = airlineInfantPricesDataSet;
    }

    @Override
    public PriceOptimisation apply(PriceOptimisation currentPriceOptimisation, Flight lob, Traveler traveler, LocalDate departure) {

        if (traveler.isAdult()) return currentPriceOptimisation;
        if (traveler.isChild()) return currentPriceOptimisation.update(currentPriceOptimisation.getOptimised() * 67 / 100, "67%");
        if (traveler.isInfant()) return currentPriceOptimisation.update(airlineInfantPricesDataSet.get(lob.getAirline()), "Infant");

        return currentPriceOptimisation;
    }

}
