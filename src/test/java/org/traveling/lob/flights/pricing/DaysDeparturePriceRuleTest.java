package org.traveling.lob.flights.pricing;

import org.junit.Before;
import org.junit.Test;
import org.traveling.domain.traveler.Traveler;
import org.traveling.lob.flights.FlightsFixtureTest;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.spi.pricing.PriceOptimisation;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DaysDeparturePriceRuleTest extends FlightsFixtureTest {

    private final DaysDeparturePriceRule daysDeparturePriceRule = new DaysDeparturePriceRule();
    private final Flight flight = FLIGHT_IB2171;
    private final Traveler traveler = Traveler.adult();
    private PriceOptimisation base = PriceOptimisation.base(100F, traveler);

    @Before
    public void setUp() throws Exception {
        base = PriceOptimisation.base(100F, traveler);
    }

    @Test
    public void shouldApply150PenaltyForDeparturesWithinLessThan3Days() throws Exception {

        LocalDate departure = LocalDate.now().plusDays(2);

        PriceOptimisation applied = daysDeparturePriceRule.apply(base, flight, traveler, departure);

        assertThat(applied.getOptimised(), is(150F));
    }

    @Test
    public void shouldApply120PenaltyForDeparturesWithin3And15Days() throws Exception {

        LocalDate departure = LocalDate.now().plusDays(15);

        PriceOptimisation applied = daysDeparturePriceRule.apply(base, flight, traveler, departure);

        assertThat(applied.getOptimised(), is(120F));
    }

    @Test
    public void shouldApplyNoPenaltyForDeparturesWithin16And30Days() throws Exception {

        LocalDate departure = LocalDate.now().plusDays(25);

        PriceOptimisation applied = daysDeparturePriceRule.apply(base, flight, traveler, departure);

        assertThat(applied.getOptimised(), is(100F));
    }

    @Test
    public void shouldApplyDiscountForDeparturesWithinMoreThan30Days() throws Exception {

        LocalDate departure = LocalDate.now().plusDays(200);

        PriceOptimisation applied = daysDeparturePriceRule.apply(base, flight, traveler, departure);

        assertThat(applied.getOptimised(), is(80F));
    }

}