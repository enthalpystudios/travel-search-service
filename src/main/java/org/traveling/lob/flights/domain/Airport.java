package org.traveling.lob.flights.domain;

public class Airport {

    private final String code;
    private final String city;

    public Airport(String code, String city) {
        this.code = code;
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public String getCity() {
        return city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;

        Airport airport = (Airport) o;

        if (code != null ? !code.equals(airport.code) : airport.code != null) return false;
        return city != null ? city.equals(airport.city) : airport.city == null;

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
