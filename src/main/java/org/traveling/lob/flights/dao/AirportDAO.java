package org.traveling.lob.flights.dao;

import org.traveling.lob.flights.domain.Airport;

import java.util.Optional;

public interface AirportDAO {

    Optional<Airport> findByCode(String code);

}
