package org.traveling.lob.flights.service;

import org.traveling.lob.flights.dao.AirportDAO;
import org.traveling.lob.flights.dao.FlightsDAO;
import org.traveling.lob.flights.domain.Airport;
import org.traveling.lob.flights.domain.Flight;

import java.util.List;
import java.util.Optional;

public class FlightsServiceImpl implements FlightsService {

    private final FlightsDAO flightsDAO;
    private final AirportDAO airportDAO;

    public FlightsServiceImpl(final FlightsDAO flightsDAO, final AirportDAO airportDAO) {
        this.flightsDAO = flightsDAO;
        this.airportDAO = airportDAO;
    }

    @Override
    public Optional<List<Flight>> lookup(final String origin, final String destination) {
        Optional<Airport> originAirport = this.airportDAO.findByCode(origin);
        Optional<Airport> destinationAirport = this.airportDAO.findByCode(destination);

        if (originAirport.isPresent() && destinationAirport.isPresent()) {
            return flightsDAO.findByOriginAndDestination(originAirport.get(), destinationAirport.get());
        }
        return Optional.empty();
    }
}
