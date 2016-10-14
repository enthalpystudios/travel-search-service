package org.traveling.spi.search;

import java.time.LocalDate;

public class SearchBuilder {

    private String origin = "";
    private String destination = "";
    private Integer adults = 0;
    private Integer children = 0;
    private Integer infants = 0;
    private LocalDate departure;

    public SearchBuilder withOrigin(String origin) {
        assert origin != null;
        assert !origin.trim().equals("");
        this.origin = origin;
        return this;
    }

    public SearchBuilder withDestination(String destination) {
        assert destination != null;
        assert !destination.trim().equals("");
        this.destination = destination;
        return this;
    }

    public SearchBuilder withAdults(Integer adults) {
        assert adults != null;
        assert adults >= 0;
        this.adults = adults;
        return this;
    }

    public SearchBuilder withChildren(Integer children) {
        assert children != null;
        assert children >= 0;
        this.children = children;
        return this;
    }

    public SearchBuilder withInfants(Integer infants) {
        assert infants != null;
        assert infants >= 0;
        this.infants = infants;
        return this;
    }

    public SearchBuilder withDeparture(LocalDate departure) {
        assert departure != null;
        assert infants >= 0;
        this.departure = departure;
        return this;
    }

    public Search build() {
        return new Search(this.origin,
                this.destination,
                this.adults,
                this.children,
                this.infants,
                this.departure);
    }

}
