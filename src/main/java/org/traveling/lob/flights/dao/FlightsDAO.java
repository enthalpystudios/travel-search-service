package org.traveling.lob.flights.dao;

import org.traveling.lob.flights.domain.Airport;
import org.traveling.lob.flights.domain.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightsDAO {

    Optional<List<Flight>> findByOriginAndDestination(Airport origin, Airport destination);

}
