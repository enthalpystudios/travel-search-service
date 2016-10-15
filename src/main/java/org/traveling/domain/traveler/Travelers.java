package org.traveling.domain.traveler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Travelers {

    private final List<Traveler> travelers;

    public Travelers(final Integer adultCount, final Integer infantCount, final Integer childrenCount) {
        List<Traveler> temp = new ArrayList<>();
        for (int i = 0; i < adultCount; i++) temp.add(Traveler.adult());
        for (int i = 0; i < infantCount; i++) temp.add(Traveler.infant());
        for (int i = 0; i < childrenCount; i++) temp.add(Traveler.child());
        this.travelers = Collections.unmodifiableList(temp);
    }

    public List<Traveler> getTravelers() {
        return travelers;
    }
}
