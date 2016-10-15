package org.traveling.lob.flights.dao;

import org.traveling.lob.flights.domain.Airport;
import org.traveling.lob.flights.domain.Flight;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FlightsDAOImpl implements FlightsDAO {

    private final Map<Airport, Map<Airport, List<Flight>>> flights;

    public FlightsDAOImpl(final Map<Airport, Map<Airport, List<Flight>>> flights) {
        this.flights = flights;
    }

    @Override
    public Optional<List<Flight>> findByOriginAndDestination(final Airport origin, final Airport destination) {
        if (flights.containsKey(origin)) {
            Map<Airport, List<Flight>> destinationsForOrigin = flights.get(origin);
            List<Flight> flightsForDestination = destinationsForOrigin.get(destination);
            return Optional.of(flightsForDestination);
        }
        return Optional.empty();
    }
}
