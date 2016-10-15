package org.traveling.util;

import static java.util.Arrays.asList;

import org.traveling.lob.flights.domain.Airline;
import org.traveling.lob.flights.domain.Airport;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.lob.flights.pricing.DaysDeparturePriceRule;
import org.traveling.lob.flights.pricing.PassengerTypePriceRule;
import org.traveling.spi.pricing.PriceRule;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FlightDataSetLoader {

    private final Map<String, Airport> airportsDataSet = new HashMap<>();
    private final Map<String, Airline> airlinesDataSet = new HashMap<>();
    private final Map<Airline, Float> airlineInfantPricesDataSet = new HashMap<>();
    private final Map<Airport, Map<Airport, List<Flight>>> flightsDataSet = new HashMap<>();
    private final List<PriceRule<Flight>> priceRules = asList(new DaysDeparturePriceRule(), new PassengerTypePriceRule(airlineInfantPricesDataSet));

    public FlightDataSetLoader load(String flightsFileName, String airportsFileName, String airlinesFileName) {
        try {
            loadAirportsDataSet(airportsFileName);
            loadAirlinesDataSet(airlinesFileName);
            loadFlightsDataSet(flightsFileName);
        } catch (Exception e) {
            System.out.println("Error loading the the data sets");
        }
        return this;
    }

    public Map<String, Airport> getAirportsDataSet() {
        return airportsDataSet;
    }

    public Map<String, Airline> getAirlinesDataSet() {
        return airlinesDataSet;
    }

    public Map<Airline, Float> getAirlineInfantPricesDataSet() {
        return airlineInfantPricesDataSet;
    }

    public Map<Airport, Map<Airport, List<Flight>>> getFlightsDataSet() {
        return flightsDataSet;
    }

    public List<PriceRule<Flight>> getPriceRules() {
        return priceRules;
    }

    private void loadAirportsDataSet(String fileName) throws Exception {
        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {
            stream.forEach(this::defineAirport);
        }
    }

    private void loadAirlinesDataSet(String fileName) throws Exception {
        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {
            stream.forEach(this::defineAirline);
        }
    }

    private void loadFlightsDataSet(String fileName) throws Exception {
        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {
            stream.forEach(this::defineFlight);
        }
    }

    private void defineAirport(String csvLine) {
        String[] fields = csvLine.split(",");
        Airport airport = new Airport(fields[0], fields[1]);
        airportsDataSet.put(airport.getCode(), airport);
    }

    private void defineAirline(String csvLine) {
        String[] fields = csvLine.split(",");
        Airline airline = new Airline(fields[0], fields[1]);
        Float price = new Float(fields[2]);
        airlinesDataSet.put(airline.getCode(), airline);
        airlineInfantPricesDataSet.put(airline, price);
    }

    private void defineFlight(String csvLine) {
        String[] fields = csvLine.split(",");

        Airport origin = airportsDataSet.get(fields[0]);
        Airport destination = airportsDataSet.get(fields[1]);
        Airline airline = airlinesDataSet.get(fields[2].substring(0, 2));
        String flightNumber = fields[2].substring(2);
        Float price = new Float(fields[3]);

        Flight flight = new Flight(origin, destination, airline, flightNumber, price);

        Map<Airport, List<Flight>> destinationsForOriginMap = flightsDataSet.get(origin);
        if (null == destinationsForOriginMap) {
            destinationsForOriginMap = new HashMap<>();
            flightsDataSet.put(origin, destinationsForOriginMap);
        }
        List<Flight> flightsForOriginAndDestination = destinationsForOriginMap.getOrDefault(destination, new ArrayList<>());
        flightsForOriginAndDestination.add(flight);
        destinationsForOriginMap.put(destination, flightsForOriginAndDestination);
    }

}
