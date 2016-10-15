package org.traveling.lob.flights.domain;

import org.traveling.domain.LineOfBusiness;

public final class Flight implements LineOfBusiness {

    private final Airport origin;
    private final Airport destination;
    private final Airline airline;
    private final String flightNumber;
    private final Float price;

    public Flight(final Airport origin, final Airport destination, final Airline airline, final String flightNumber, final Float price) {
        this.origin = origin;
        this.destination = destination;
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.price = price;
    }

    public Airport getOrigin() {
        return origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public Airline getAirline() {
        return airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    @Override
    public Float getPrice() {
        return price;
    }

    public String getAirlineFlightNumber() {
        return this.airline.getCode().concat(this.flightNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;

        Flight flight = (Flight) o;

        if (getOrigin() != null ? !getOrigin().equals(flight.getOrigin()) : flight.getOrigin() != null) return false;
        if (getDestination() != null ? !getDestination().equals(flight.getDestination()) : flight.getDestination() != null)
            return false;
        if (getAirline() != null ? !getAirline().equals(flight.getAirline()) : flight.getAirline() != null)
            return false;
        if (getFlightNumber() != null ? !getFlightNumber().equals(flight.getFlightNumber()) : flight.getFlightNumber() != null)
            return false;
        return getPrice() != null ? getPrice().equals(flight.getPrice()) : flight.getPrice() == null;

    }

    @Override
    public int hashCode() {
        int result = getOrigin() != null ? getOrigin().hashCode() : 0;
        result = 31 * result + (getDestination() != null ? getDestination().hashCode() : 0);
        result = 31 * result + (getAirline() != null ? getAirline().hashCode() : 0);
        result = 31 * result + (getFlightNumber() != null ? getFlightNumber().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        return result;
    }
}
