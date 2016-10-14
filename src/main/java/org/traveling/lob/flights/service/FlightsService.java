package org.traveling.lob.flights.service;

import org.traveling.lob.flights.domain.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightsService {

    Optional<List<Flight>> lookup(String origin, String destination);

}
