package org.traveling.lob.flights.pricing;

import org.junit.Test;
import org.traveling.domain.traveler.Traveler;
import org.traveling.domain.traveler.Travelers;
import org.traveling.lob.flights.FlightsFixtureTest;
import org.traveling.lob.flights.domain.Airline;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.spi.pricing.PriceOptimisation;
import org.traveling.spi.pricing.PriceRule;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class FlightPriceOptimisationEngineTest extends FlightsFixtureTest {

    private final Map<Airline, Float> airlineInfantsPrices = new HashMap<Airline, Float>() {{
        put(FLIGHT_LH1085.getAirline(), 9.5F);
    }};
    private final PassengerTypePriceRule passengerTypePriceRule = new PassengerTypePriceRule(airlineInfantsPrices);
    private final DaysDeparturePriceRule daysDeparturePriceRule = new DaysDeparturePriceRule();
    private final List<PriceRule<Flight>> orderedRules = new ArrayList<PriceRule<Flight>>(2) {{
        add(daysDeparturePriceRule);
        add(passengerTypePriceRule);
    }};
    private final List<Flight> flights = new ArrayList<Flight>() {{
        add(FLIGHT_LH1085);
    }};
    private final FlightPriceOptimisationEngine flightPriceOptimisationEngine = new FlightPriceOptimisationEngine(orderedRules);

    @Test
    public void shouldOptimisePriceForSeveralRules() throws Exception {

        LocalDate departure = LocalDate.now().plusDays(2);

        Map<Flight, Map<Traveler, PriceOptimisation>> optimisation = flightPriceOptimisationEngine.optimise(flights, new Travelers(0, 0, 1), departure);

        assertNotNull(optimisation);
        assertTrue(optimisation.containsKey(FLIGHT_LH1085));
        Optional<PriceOptimisation> priceOptimisation = optimisation.get(FLIGHT_LH1085).values().stream().findFirst();
        assertTrue(priceOptimisation.isPresent());
        assertThat(priceOptimisation.get().getOptimised(), is(148.74F));

    }

}