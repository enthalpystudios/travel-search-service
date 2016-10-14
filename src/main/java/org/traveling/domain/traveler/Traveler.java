package org.traveling.domain.traveler;

public class Traveler {

    private TravelerType travelerType;

    private Traveler(TravelerType travelerType) {
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

    public boolean isAdult() {
        return this.travelerType.equals(TravelerType.ADULT);
    }

    public boolean isChild() {
        return this.travelerType.equals(TravelerType.CHILD);
    }

    public boolean isInfant() {
        return this.travelerType.equals(TravelerType.INFANT);
    }

    public TravelerType getTravelerType() {
        return travelerType;
    }
}
