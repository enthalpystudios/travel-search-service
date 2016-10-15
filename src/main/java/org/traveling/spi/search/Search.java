package org.traveling.spi.search;

import org.traveling.domain.traveler.Travelers;

import java.time.LocalDate;

public class Search {

    private final String origin;
    private final String destination;
    private final Travelers travelers;
    private final LocalDate departure;

    Search(final String origin, final String destination, final Integer adults, final Integer children, final Integer infants, final LocalDate departure) {
        this.origin = origin;
        this.destination = destination;
        this.travelers = new Travelers(adults, infants, children);
        this.departure = departure;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public Travelers getTravelers() {
        return travelers;
    }

    public LocalDate getDeparture() {
        return departure;
    }
}
