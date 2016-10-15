package org.traveling.lob.flights.domain;

public final class Airline {

    private final String code;
    private final String name;

    public Airline(final String code, final String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airline)) return false;

        Airline airline = (Airline) o;

        if (code != null ? !code.equals(airline.code) : airline.code != null) return false;
        return name != null ? name.equals(airline.name) : airline.name == null;

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

