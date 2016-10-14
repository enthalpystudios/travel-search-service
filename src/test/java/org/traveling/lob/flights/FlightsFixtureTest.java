package org.traveling.lob.flights;

import org.traveling.lob.flights.domain.Airline;
import org.traveling.lob.flights.domain.Airport;
import org.traveling.lob.flights.domain.Flight;
import org.traveling.util.FlightDataSetLoader;

public class FlightsFixtureTest {

    protected final FlightDataSetLoader loader;

    protected Flight FLIGHT_TK8891;
    protected Flight FLIGHT_LH1085;
    protected Flight FLIGHT_IB2171;
    protected Flight FLIGHT_LH5496;
    protected Flight FLIGHT_LH5909;
    protected Flight FLIGHT_TK2659;
    protected Flight FLIGHT_TK2372;

    protected FlightsFixtureTest() {
        loader = new FlightDataSetLoader().load("flights.csv", "airports.csv", "airlines.csv");
        load();
    }

    protected void load() {
        FLIGHT_TK8891 = getFlight("LHR", "IST", "TK", "8891", 250F);
        FLIGHT_LH1085 = getFlight("LHR", "IST", "LH", "1085", 148F);

        FLIGHT_IB2171 = getFlight("BCN", "MAD", "IB", "2171", 259F);
        FLIGHT_LH5496 = getFlight("BCN", "MAD", "LH", "5496", 293F);

        FLIGHT_LH5909 = getFlight("AMS", "FRA", "LH", "5909", 113F);
        FLIGHT_TK2659 = getFlight("AMS", "FRA", "TK", "2659", 248F);
        FLIGHT_TK2372 = getFlight("AMS", "FRA", "TK", "2372", 197F);

    }

    // utils methods
    private Flight getFlight(String aOrigin, String aDestination, String aAirline, String flightNumber, Float aPrice) {
        Airport origin = loader.getAirportsDataSet().get(aOrigin);
        Airport destination = loader.getAirportsDataSet().get(aDestination);
        Airline airline = loader.getAirlinesDataSet().get(aAirline);
        return new Flight(origin, destination, airline, flightNumber, aPrice);
    }


}
