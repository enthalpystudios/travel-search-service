package org.traveling.lob.flights.search;

import org.junit.Test;
import org.traveling.domain.traveler.TravelerType;
import org.traveling.lob.flights.FlightsFixtureTest;
import org.traveling.lob.flights.dao.AirportDAO;
import org.traveling.lob.flights.dao.AirportDAOImpl;
import org.traveling.lob.flights.dao.FlightsDAO;
import org.traveling.lob.flights.dao.FlightsDAOImpl;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.lob.flights.domain.FlightSummary;
import org.traveling.lob.flights.pricing.DaysDeparturePriceRule;
import org.traveling.lob.flights.pricing.FlightPriceOptimisationEngine;
import org.traveling.lob.flights.pricing.PassengerTypePriceRule;
import org.traveling.lob.flights.service.FlightsService;
import org.traveling.lob.flights.service.FlightsServiceImpl;
import org.traveling.spi.pricing.PriceOptimisation;
import org.traveling.spi.pricing.PriceRule;
import org.traveling.spi.search.Search;
import org.traveling.spi.search.SearchBuilder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class FlightSearchServiceImplTest extends FlightsFixtureTest {

    private FlightSearchServiceImpl flightSearchService;

    public FlightSearchServiceImplTest() throws Exception {
        FlightsDAO flightsDAO = new FlightsDAOImpl(loader.getFlightsDataSet());
        AirportDAO airportDAO = new AirportDAOImpl(loader.getAirportsDataSet());
        FlightsService flightsService = new FlightsServiceImpl(flightsDAO, airportDAO);
        List<PriceRule<Flight>> priceRules = new ArrayList<PriceRule<Flight>>() {{
            add(new DaysDeparturePriceRule());
            add(new PassengerTypePriceRule(loader.getAirlineInfantPricesDataSet()));
        }};
        FlightPriceOptimisationEngine flightPriceOptimisationEngine = new FlightPriceOptimisationEngine(priceRules);
        flightSearchService = new FlightSearchServiceImpl(flightsService, flightPriceOptimisationEngine);
    }

    @Test
    public void shouldReturnEmptyResultsWhenNoResultsForGivenSearch() {

        // given a default search
        Search searchWithNoResults = new SearchBuilder()
                .withOrigin("CGD")
                .withDestination("FRA")
                .build();

        // when the search service is called
        FlightSearchResult flightSearchResult = flightSearchService.search(searchWithNoResults);

        // then no results should be given
        assertThat(flightSearchResult.isEmpty(), is(true));

    }

    @Test
    public void shouldReturnThreeFlightsForOneAdultFromAMSToFRA() {

        // 1 adult, 31 days to the departure date, flying AMS -> FRA

        Search search = new SearchBuilder()
                .withAdults(1)
                .withOrigin("AMS")
                .withDestination("FRA")
                .withDeparture(LocalDate.now().plus(31, ChronoUnit.DAYS))
                .build();

        // when the search service is called
        FlightSearchResult flightSearchResult = flightSearchService.search(search);

        // then no results should be given
        List<FlightSummary> flightSummaries = flightSearchResult.getResult();
        assertThat(flightSummaries.size(), is(3));

        FlightSummary flightSummaryLH5909 = flightSummaries.get(0);
        assertThat(flightSummaryLH5909.getTotalPrice(), is(90.4F));
        assertThat(flightSummaryLH5909.getFlight(), is(FLIGHT_LH5909));

        FlightSummary flightSummaryTK2372 = flightSummaries.get(1);
        assertThat(flightSummaryTK2372.getTotalPrice(), is(157.6F));
        assertThat(flightSummaryTK2372.getFlight(), is(FLIGHT_TK2372));

        FlightSummary flightSummaryTK2659 = flightSummaries.get(2);
        assertThat(flightSummaryTK2659.getTotalPrice(), is(198.4F));
        assertThat(flightSummaryTK2659.getFlight(), is(FLIGHT_TK2659));

    }

    @Test
    public void shouldReturnTwoFlightsFromLHRToIST() {

        // 2 adults, 1 child, 1 infant, 15 days to the departure date, flying LHR -> IST
        Search search = new SearchBuilder()
                .withAdults(2)
                .withChildren(1)
                .withInfants(1)
                .withOrigin("LHR")
                .withDestination("IST")
                .withDeparture(LocalDate.now().plus(15, ChronoUnit.DAYS))
                .build();

        // when the search service is called
        FlightSearchResult flightSearchResult = flightSearchService.search(search);

        // then there should be two queryable results
        assertNotNull(flightSearchResult);
        List<FlightSummary> flightSummaries = flightSearchResult.getResult();
        assertThat(flightSummaries.size(), is(2));

        // and should contain the expected flights
        FlightSummary flightSummaryForLH1085 = flightSummaries.get(0);
        assertFlightLH1085ForTwoAdultsAndOneChildAndOneInfant(flightSummaryForLH1085);

        // and for flight TK8891 prices
        FlightSummary flightSummaryForTK8891 = flightSummaries.get(1);
        assertFlightTK8891ForTwoAdultsAndOneChildAndOneInfant(flightSummaryForTK8891);

    }

    @Test
    public void shouldReturnTwoFlightsFromBCNToMAD() {

        // 1 adult, 2 children, 2 days to the departure date, flying BCN -> MAD
        Search search = new SearchBuilder()
                .withAdults(1)
                .withChildren(2)
                .withOrigin("BCN")
                .withDestination("MAD")
                .withDeparture(LocalDate.now().plus(2, ChronoUnit.DAYS))
                .build();

        // when the search service is called
        FlightSearchResult flightSearchResult = flightSearchService.search(search);

        // then there should be two queryable results
        assertNotNull(flightSearchResult);
        List<FlightSummary> flightSummaries = flightSearchResult.getResult();
        assertThat(flightSummaries.size(), is(2));

        // and should contain the expected flights
        FlightSummary flightSummaryForIB2171 = flightSummaries.get(0);
        assertFlightIB2171ForOneAdultAndTwoChild(flightSummaryForIB2171);

        // and for flight LH5496 prices
        FlightSummary flightSummaryForLH5496 = flightSummaries.get(1);
        assertFlightLH5496ForOneAdultAndTwoChild(flightSummaryForLH5496);

    }

    private void assertFlightIB2171ForOneAdultAndTwoChild(FlightSummary flightSummary) {
        Map<TravelerType, List<PriceOptimisation>> travelerPriceOptimisations = flightSummary.asQueryable();
        assertNotNull(travelerPriceOptimisations);

        assertThat(flightSummary.getFlight(), is(FLIGHT_IB2171));

        // there should be the expected prices for 1 adult
        List<PriceOptimisation> pricesForAdults = travelerPriceOptimisations.get(TravelerType.ADULT);
        assertThat(pricesForAdults.size(), is(1));
        assertThat(pricesForAdults.get(0).getOptimised(), is(388.5F));

        // and there should be the expected price for two children
        List<PriceOptimisation> pricesForChildren = travelerPriceOptimisations.get(TravelerType.CHILD);
        assertThat(pricesForChildren.size(), is(2));
        assertThat(pricesForChildren.get(0).getOptimised(), is(260.295F));
        assertThat(pricesForChildren.get(1).getOptimised(), is(260.295F));

        // and total price of the flight is
        assertThat(flightSummary.getTotalPrice(), is(909.09F));
    }

    private void assertFlightLH5496ForOneAdultAndTwoChild(FlightSummary flightSummary) {
        Map<TravelerType, List<PriceOptimisation>> travelerPriceOptimisations = flightSummary.asQueryable();
        assertNotNull(travelerPriceOptimisations);

        assertThat(flightSummary.getFlight(), is(FLIGHT_LH5496));

        // there should be the expected prices for 1 adult
        List<PriceOptimisation> pricesForAdults = travelerPriceOptimisations.get(TravelerType.ADULT);
        assertThat(pricesForAdults.size(), is(1));
        assertThat(pricesForAdults.get(0).getOptimised(), is(439.5F));

        // and there should be the expected price for two children
        List<PriceOptimisation> pricesForChildren = travelerPriceOptimisations.get(TravelerType.CHILD);
        assertThat(pricesForChildren.size(), is(2));
        assertThat(pricesForChildren.get(0).getOptimised(), is(294.465F));
        assertThat(pricesForChildren.get(1).getOptimised(), is(294.465F));

        // and total price of the flight is
        assertThat(flightSummary.getTotalPrice(), is(1028.43F));
    }

    private void assertFlightTK8891ForTwoAdultsAndOneChildAndOneInfant(FlightSummary flightSummary) {
        Map<TravelerType, List<PriceOptimisation>> travelerPriceOptimisations = flightSummary.asQueryable();
        assertNotNull(travelerPriceOptimisations);

        assertThat(flightSummary.getFlight(), is(FLIGHT_TK8891));

        // there should be the expected prices for 2 adults
        List<PriceOptimisation> pricesForAdults = travelerPriceOptimisations.get(TravelerType.ADULT);
        assertThat(pricesForAdults.size(), is(2));
        assertThat(pricesForAdults.get(0).getOptimised(), is(300F));
        assertThat(pricesForAdults.get(1).getOptimised(), is(300F));

        // and there should be the expected price for one child
        List<PriceOptimisation> pricesForChildren = travelerPriceOptimisations.get(TravelerType.CHILD);
        assertThat(pricesForChildren.size(), is(1));
        assertThat(pricesForChildren.get(0).getOptimised(), is(201.0F));

        // and there should be the expected price for one infant
        List<PriceOptimisation> pricesForInfant = travelerPriceOptimisations.get(TravelerType.INFANT);
        assertThat(pricesForInfant.size(), is(1));
        assertThat(pricesForInfant.get(0).getOptimised(), is(5F));

        // and total price of the flight is
        assertThat(flightSummary.getTotalPrice(), is(806.0F));
    }

    private void assertFlightLH1085ForTwoAdultsAndOneChildAndOneInfant(FlightSummary flightSummary) {
        Map<TravelerType, List<PriceOptimisation>> travelerPriceOptimisations = flightSummary.asQueryable();
        assertNotNull(travelerPriceOptimisations);

        assertThat(flightSummary.getFlight(), is(FLIGHT_LH1085));

        // there should be the expected prices for 2 adults
        List<PriceOptimisation> pricesForAdults = travelerPriceOptimisations.get(TravelerType.ADULT);
        assertThat(pricesForAdults.size(), is(2));
        assertThat(pricesForAdults.get(0).getOptimised(), is(177.6F));
        assertThat(pricesForAdults.get(1).getOptimised(), is(177.6F));

        // and there should be the expected price for one child
        List<PriceOptimisation> pricesForChildren = travelerPriceOptimisations.get(TravelerType.CHILD);
        assertThat(pricesForChildren.size(), is(1));
        assertThat(pricesForChildren.get(0).getOptimised(), is(118.992004F));

        // and there should be the expected price for one infant
        List<PriceOptimisation> pricesForInfant = travelerPriceOptimisations.get(TravelerType.INFANT);
        assertThat(pricesForInfant.size(), is(1));
        assertThat(pricesForInfant.get(0).getOptimised(), is(7F));

        // and total price of the flight is
        assertThat(flightSummary.getTotalPrice(), is(481.19F));
    }

}