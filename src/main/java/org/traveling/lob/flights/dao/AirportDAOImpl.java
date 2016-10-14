package org.traveling.lob.flights.dao;

import org.traveling.lob.flights.domain.Airport;

import java.util.Map;
import java.util.Optional;

public class AirportDAOImpl implements AirportDAO {

    private final Map<String, Airport> airports;

    public AirportDAOImpl(Map<String, Airport> airports) {
        this.airports = airports;
    }

    @Override
    public Optional<Airport> findByCode(String code) {
        return Optional.ofNullable(airports.get(code));
    }
}
