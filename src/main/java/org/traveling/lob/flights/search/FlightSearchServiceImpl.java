package org.traveling.lob.flights.search;

import org.traveling.domain.traveler.Traveler;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.lob.flights.domain.FlightSummary;
import org.traveling.spi.pricing.PriceOptimisation;
import org.traveling.domain.traveler.Travelers;
import org.traveling.spi.search.Search;
import org.traveling.lob.flights.service.FlightsService;
import org.traveling.spi.pricing.PriceOptimisationEngine;
import org.traveling.spi.search.SearchService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FlightSearchServiceImpl implements SearchService<FlightSearchResult> {

    private final FlightsService flightsService;
    private final PriceOptimisationEngine priceOptimisationEngine;

    public FlightSearchServiceImpl(final FlightsService flightsService, final PriceOptimisationEngine priceOptimisationEngine) {
        this.flightsService = flightsService;
        this.priceOptimisationEngine = priceOptimisationEngine;
    }

    @Override
    public FlightSearchResult search(final Search search) {
        Optional<List<Flight>> maybeFlights = flightsService.lookup(search.getOrigin(), search.getDestination());
        Travelers travelers = search.getTravelers();
        LocalDate departure = search.getDeparture();

        if (maybeFlights.isPresent()) {
            List<Flight> flights = maybeFlights.get();
            Map<Flight, Map<Traveler, PriceOptimisation>> priceOptimisationsForFlights = priceOptimisationEngine.optimise(flights, travelers, departure);
            List<FlightSummary> flightSummaries = toFlightSummaries(priceOptimisationsForFlights);
            flightSummaries.sort((o1, o2) -> o1
                    .getFlight()
                    .getAirlineFlightNumber().compareTo(o2
                            .getFlight()
                            .getAirlineFlightNumber()));
            return new FlightSearchResult(flightSummaries);
        }

        return FlightSearchResult.empty();
    }

    private List<FlightSummary> toFlightSummaries(Map<Flight, Map<Traveler, PriceOptimisation>> priceOptimisationsForFlights) {
        List<FlightSummary> flightSummaries = new ArrayList<>();

        priceOptimisationsForFlights.entrySet().forEach((priceForFlightEntry) -> {
            Flight flight = priceForFlightEntry.getKey();
            List<PriceOptimisation> priceOptimisations = new ArrayList<>(priceForFlightEntry.getValue().values());
            flightSummaries.add(new FlightSummary(flight, priceOptimisations));
        });

        return flightSummaries;
    }
}
