package org.traveling.lob.flights.pricing;

import org.junit.Test;
import org.traveling.domain.traveler.Traveler;
import org.traveling.lob.flights.FlightsFixtureTest;
import org.traveling.lob.flights.domain.Airline;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.spi.pricing.PriceOptimisation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PassengerTypePriceRuleTest extends FlightsFixtureTest {

    private final Map<Airline, Float> airlineInfantsPrices = new HashMap<Airline, Float>() {{
        put(FLIGHT_LH1085.getAirline(), 9.5F);
    }};
    private final PassengerTypePriceRule passengerTypePriceRule = new PassengerTypePriceRule(airlineInfantsPrices);
    private final Flight flight = FLIGHT_LH1085;

    @Test
    public void shouldNotApplyAnyPriceOptimisationToAdult() throws Exception {

        Traveler adult = Traveler.adult();
        PriceOptimisation base = PriceOptimisation.base(100F, adult);
        LocalDate departure = LocalDate.now().plusDays(8);

        PriceOptimisation applied = passengerTypePriceRule.apply(base, flight, adult, departure);

        assertThat(applied.getOptimised(), is(100F));
    }

    @Test
    public void shouldApply33PercentDiscountToChildren() throws Exception {

        Traveler child = Traveler.child();
        PriceOptimisation base = PriceOptimisation.base(100F, child);
        LocalDate departure = LocalDate.now().plusDays(8);

        PriceOptimisation applied = passengerTypePriceRule.apply(base, flight, child, departure);

        assertThat(applied.getOptimised(), is(67F));
    }

    @Test
    public void shouldGetFlatFarFromAirlineForInfants() throws Exception {

        Traveler infant = Traveler.infant();
        PriceOptimisation base = PriceOptimisation.base(100F, infant);
        LocalDate departure = LocalDate.now().plusDays(8);

        PriceOptimisation applied = passengerTypePriceRule.apply(base, flight, infant, departure);

        assertThat(applied.getOptimised(), is(9.5F));
    }

}