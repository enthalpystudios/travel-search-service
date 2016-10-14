package org.traveling.lob.flights.dao;

import org.junit.Test;
import org.traveling.lob.flights.FlightsFixtureTest;
import org.traveling.lob.flights.domain.Airport;
import org.traveling.lob.flights.domain.Flight;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FlightsDAOImplTest extends FlightsFixtureTest {

    @Test
    public void shouldFindThreeFlightsFromAMSToFRA() throws Exception {
        // given flights dao with a standard flights dataset
        FlightsDAO flightsDAO = new FlightsDAOImpl(loader.getFlightsDataSet());
        Airport origin = new Airport("AMS", "Amsterdam");
        Airport destination = new Airport("FRA", "Frakfurt");

        // when
        Optional<List<Flight>> byOriginAndDestination = flightsDAO.findByOriginAndDestination(origin, destination);

        // then there should be some results
        assertTrue(byOriginAndDestination.isPresent());
        assertThat(byOriginAndDestination.get().size(), is(3));
        assertTrue(byOriginAndDestination.get().contains(FLIGHT_TK2372));
        assertTrue(byOriginAndDestination.get().contains(FLIGHT_TK2659));
        assertTrue(byOriginAndDestination.get().contains(FLIGHT_LH5909));
    }

    @Test
    public void shouldNotFindAnyFlightsFromOPOToLHR() throws Exception {
        // given flights dao with a standard flights dataset
        FlightsDAO flightsDAO = new FlightsDAOImpl(loader.getFlightsDataSet());
        Airport origin = new Airport("OPO", "Oporto");
        Airport destination = new Airport("LHR", "London Heathrow");

        // when
        Optional<List<Flight>> byOriginAndDestination = flightsDAO.findByOriginAndDestination(origin, destination);

        // then there should be no results
        assertFalse(byOriginAndDestination.isPresent());
    }

}