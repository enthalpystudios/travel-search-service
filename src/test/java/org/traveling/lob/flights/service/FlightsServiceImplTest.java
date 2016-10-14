package org.traveling.lob.flights.service;

import org.junit.Before;
import org.junit.Test;
import org.traveling.lob.flights.dao.AirportDAO;
import org.traveling.lob.flights.dao.FlightsDAO;
import org.traveling.lob.flights.domain.Airline;
import org.traveling.lob.flights.domain.Airport;
import org.traveling.lob.flights.domain.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FlightsServiceImplTest {

    private FlightsService flightsService;

    private FlightsDAO flightsDAO;
    private AirportDAO airportDAO;

    @Before
    public void setUp() throws Exception {
        flightsDAO = mock(FlightsDAO.class);
        airportDAO = mock(AirportDAO.class);
        flightsService = new FlightsServiceImpl(flightsDAO, airportDAO);
    }

    @Test
    public void shouldLookForFlight() throws Exception {

        // given the origin airport OPO
        Airport origin = new Airport("OPO", "Oporto");
        given(airportDAO.findByCode("OPO")).willReturn(Optional.of(origin));
        // and the destination airport
        Airport destination = new Airport("LHR", "London Heathrow");
        given(airportDAO.findByCode("LHR")).willReturn(Optional.of(destination));

        List<Flight> flights = new ArrayList<Flight>() {{
            add(new Flight(origin, destination, new Airline("TAP", "Air Portugal"), "TP0001", 10000F));
        }};
        Optional<List<Flight>> expectedLookupResult = Optional.of(flights);
        given(flightsDAO.findByOriginAndDestination(origin, destination)).willReturn(expectedLookupResult);

        Optional<List<Flight>> result = flightsService.lookup("OPO", "LHR");

        assertThat(result, is(expectedLookupResult));
    }

}