package org.traveling.domain.traveler;

public enum TravelerType {
    ADULT,
    CHILD,
    INFANT;

    public boolean isAdult() { return this == ADULT; }
    public boolean isChild() { return this == CHILD; }
    public boolean isInfant() { return this == INFANT; }
}
