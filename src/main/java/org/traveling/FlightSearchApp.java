package org.traveling;

import org.traveling.lob.flights.dao.AirportDAO;
import org.traveling.lob.flights.dao.AirportDAOImpl;
import org.traveling.lob.flights.dao.FlightsDAO;
import org.traveling.lob.flights.dao.FlightsDAOImpl;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.lob.flights.pricing.FlightPriceOptimisationEngine;
import org.traveling.lob.flights.search.FlightSearchResult;
import org.traveling.lob.flights.search.FlightSearchServiceImpl;
import org.traveling.lob.flights.service.FlightsService;
import org.traveling.lob.flights.service.FlightsServiceImpl;
import org.traveling.spi.pricing.PriceOptimisationEngine;
import org.traveling.spi.search.Search;
import org.traveling.spi.search.SearchBuilder;
import org.traveling.spi.search.SearchService;
import org.traveling.util.FlightDataSetLoader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Simple FlightSearchApp demonstration.
 * It loads "flights.csv", "airports.csv" and "airlines.csv" files as datasource
 * Input: "origin" "destination" 'adults' 'children' 'infants' "date as dd-MM-yyyy"
 * Example: "BCN" "MAD" 1 2 0 "13-10-2016"
 */
public class FlightSearchApp {

    public static void main(String[] args) {

        FlightDataSetLoader loader = new FlightDataSetLoader()
                .load("flights.csv", "airports.csv", "airlines.csv");

        FlightsDAO flightsDAO = new FlightsDAOImpl(loader.getFlightsDataSet());
        AirportDAO airportDAO = new AirportDAOImpl(loader.getAirportsDataSet());
        FlightsService flightsService = new FlightsServiceImpl(flightsDAO, airportDAO);
        PriceOptimisationEngine<Flight> flightPriceOptimisationEngine = new FlightPriceOptimisationEngine(loader.getPriceRules());
        SearchService<FlightSearchResult> flightSearchService = new FlightSearchServiceImpl(flightsService, flightPriceOptimisationEngine);

        String origin = args[0];
        String destination = args[1];
        Integer adults = new Integer(args[2]);
        Integer children = new Integer(args[3]);
        Integer infants = new Integer(args[4]);
        LocalDate departure = LocalDate.parse(args[5], DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Search search = new SearchBuilder()
                .withOrigin(origin)
                .withDestination(destination)
                .withAdults(adults)
                .withChildren(children)
                .withInfants(infants)
                .withDeparture(departure)
                .build();

        doSearch(flightSearchService, origin, destination, adults, children, infants, departure, search);

    }

    private static void doSearch(SearchService<FlightSearchResult> flightSearchService, String origin, String destination, Integer adults, Integer children, Integer infants, LocalDate departure, Search search) {
        FlightSearchResult flightSearchResult = flightSearchService.search(search);

        StringBuilder informationSb = new StringBuilder();
        informationSb = informationSb
                .append("Results for Flight from ")
                .append(origin)
                .append(" to ")
                .append(destination)
                .append(" and ")
                .append(adults)
                .append(" adults")
                .append(", ")
                .append(children)
                .append(" children")
                .append(", ")
                .append(infants)
                .append(" infants")
                .append(" on the date ")
                .append(departure);
        System.out.println(informationSb.toString());

        if (flightSearchResult.isEmpty()) {
            System.out.println("no flights available");
        } else {
            flightSearchResult.getResult().forEach(flightSummary -> {
                String summarySb = "* " +
                        flightSummary.getFlight().getAirlineFlightNumber() +
                        ", " +
                        flightSummary.getTotalPrice() +
                        " €";
                System.out.println(summarySb);
            });
        }
    }

}
