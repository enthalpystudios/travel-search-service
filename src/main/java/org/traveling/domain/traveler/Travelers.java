package org.traveling.domain.traveler;

import java.util.ArrayList;
import java.util.List;

public class Travelers {

    private final List<Traveler> travelers = new ArrayList<>();

    public Travelers(Integer adultCount, Integer infantCount, Integer childrenCount) {
        for (int i = 0; i < adultCount; i++) travelers.add(Traveler.adult());
        for (int i = 0; i < infantCount; i++) travelers.add(Traveler.infant());
        for (int i = 0; i < childrenCount; i++) travelers.add(Traveler.child());
    }

    public List<Traveler> getTravelers() {
        return travelers;
    }
}
