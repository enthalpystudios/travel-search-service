package org.traveling.lob.flights.search;

import org.traveling.lob.flights.domain.Flight;
import org.traveling.lob.flights.domain.FlightSummary;
import org.traveling.spi.search.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class FlightSearchResult implements SearchResult<Flight> {

    private List<FlightSummary> result;
    private boolean isEmpty = true;

    public static FlightSearchResult empty() {
        return new FlightSearchResult(new ArrayList<>());
    }

    public FlightSearchResult(List<FlightSummary> result) {
        this.result = result;
        this.isEmpty = result == null || result.isEmpty();
    }

    public List<FlightSummary> getResult() {
        if (null == this.result) {
            result = new ArrayList<>();
        }
        return result;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

}
