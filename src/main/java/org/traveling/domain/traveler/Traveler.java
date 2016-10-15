package org.traveling.domain.traveler;

public class Traveler {
    private final TravelerType travelerType;

    public Traveler(final TravelerType travelerType) {
        this.travelerType = travelerType;
    }

    public static Traveler adult() {
        return new Traveler(TravelerType.ADULT);
    }

    public static Traveler infant() {
        return new Traveler(TravelerType.INFANT);
    }

    public static Traveler child() {
        return new Traveler(TravelerType.CHILD);
    }

    public TravelerType getTravelerType() {
        return travelerType;
    }
}
