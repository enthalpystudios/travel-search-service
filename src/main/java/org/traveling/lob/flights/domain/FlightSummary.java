package org.traveling.lob.flights.domain;

import org.traveling.domain.traveler.TravelerType;
import org.traveling.spi.pricing.PriceOptimisation;

import java.util.*;

public class FlightSummary {

    private final Flight flight;
    private Float lazyTotalPrice = null;
    private final List<PriceOptimisation> priceOptimisations;

    public FlightSummary(Flight flight, List<PriceOptimisation> priceOptimisations) {
        this.flight = flight;
        this.priceOptimisations = priceOptimisations;
    }

    public Flight getFlight() {
        return flight;
    }

    public Float getTotalPrice() {
        if (lazyTotalPrice == null) {
            Optional<Float> sumOfAllOptimisations = priceOptimisations
                    .stream()
                    .map(PriceOptimisation::getOptimised)
                    .reduce((a, b) -> a + b);
            lazyTotalPrice = Math.round(sumOfAllOptimisations.orElse(0F) * 100.0f)/100.0f;
        }
        return lazyTotalPrice;
    }

    public List<PriceOptimisation> getPriceOptimisations() {
        return priceOptimisations;
    }

    public Map<TravelerType, List<PriceOptimisation>> asQueryable() {
        Map<TravelerType, List<PriceOptimisation>> queryable = new HashMap<>();
        this.getPriceOptimisations().forEach((priceOptimisation) -> {
            TravelerType travelerType = priceOptimisation.getTraveler().getTravelerType();
            queryable.putIfAbsent(travelerType, new ArrayList<>());
            queryable.get(travelerType).add(priceOptimisation);
        });
        return queryable;
    }
}
