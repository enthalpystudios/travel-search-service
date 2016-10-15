package org.traveling.lob.flights.pricing;

import org.traveling.domain.traveler.Traveler;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.domain.traveler.Travelers;
import org.traveling.spi.pricing.PriceOptimisation;
import org.traveling.spi.pricing.PriceOptimisationEngine;
import org.traveling.spi.pricing.PriceRule;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightPriceOptimisationEngine implements PriceOptimisationEngine<Flight> {

    private final List<PriceRule<Flight>> orderedRules;

    public FlightPriceOptimisationEngine(List<PriceRule<Flight>> orderedRules) {
        assert orderedRules != null;
        assert !orderedRules.isEmpty();
        this.orderedRules = orderedRules;
    }

    @Override
    public Map<Flight, Map<Traveler, PriceOptimisation>> optimise(List<Flight> lobs, Travelers travelers, LocalDate departure) {
        Map<Flight, Map<Traveler, PriceOptimisation>> optimisedPrices = new HashMap<>();

        lobs.forEach((lob) -> {
            travelers.getTravelers().forEach((traveler) -> {
                orderedRules.stream().forEach((priceRule) -> {
                    PriceOptimisation currentPriceOptimisation = getPriceOptimisationForFlightAndTraveler(lob, traveler, optimisedPrices);
                    PriceOptimisation updatedPriceOptimisation = priceRule.apply(currentPriceOptimisation, lob, traveler, departure);
                    optimisedPrices.get(lob).put(traveler, updatedPriceOptimisation);
                });
            });
        });
        return optimisedPrices;
    }

    private PriceOptimisation getPriceOptimisationForFlightAndTraveler(Flight flight,
                                                                       Traveler traveler,
                                                                       Map<Flight, Map<Traveler, PriceOptimisation>> allOptimisedPrices) {
        allOptimisedPrices.putIfAbsent(flight, new HashMap<>());
        allOptimisedPrices.get(flight).putIfAbsent(traveler, PriceOptimisation.base(flight.getPrice(), traveler));
        return allOptimisedPrices.get(flight).get(traveler);
    }

}
