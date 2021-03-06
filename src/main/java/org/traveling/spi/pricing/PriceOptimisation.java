package org.traveling.spi.pricing;

import org.traveling.domain.traveler.Traveler;

import java.util.ArrayList;
import java.util.List;

public final class PriceOptimisation {

    private final Float base;
    private final Traveler traveler;
    private final List<String> reasons = new ArrayList<>();
    private Float optimised;

    private PriceOptimisation(final Float base, final Traveler traveler) {
        this.base = base;
        this.optimised = base;
        this.traveler = traveler;
    }

    public Float getBase() {
        return base;
    }

    public Float getOptimised() {
        return optimised;
    }

    public Traveler getTraveler() {
        return traveler;
    }

    public static PriceOptimisation base(final Float base, final Traveler traveler) {
        return new PriceOptimisation(base, traveler);
    }

    public PriceOptimisation update(final Float optimised, final String reason) {
        this.optimised = optimised;
        this.reasons.add(reason);
        return this;
    }

    public List<String> getReasons() {
        return reasons;
    }
}
