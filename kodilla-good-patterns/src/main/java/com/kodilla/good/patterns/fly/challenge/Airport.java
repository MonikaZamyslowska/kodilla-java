package com.kodilla.good.patterns.fly.challenge;

import java.time.LocalDateTime;
import java.util.Objects;

public class Airport {

    private String departureAirport;
    private String arrivalAirport;
    private LocalDateTime nextDeparture;

    public Airport(String departureAirport, String arrivalAirport, LocalDateTime departureDay) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.nextDeparture = departureDay;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public LocalDateTime getNextDeparture() {
        return nextDeparture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Airport)) return false;
        Airport airport = (Airport) o;
        return Objects.equals(getDepartureAirport(), airport.getDepartureAirport()) &&
                Objects.equals(getArrivalAirport(), airport.getArrivalAirport()) &&
                Objects.equals(getNextDeparture(), airport.getNextDeparture());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDepartureAirport(), getArrivalAirport(), getNextDeparture());
    }

    @Override
    public String toString() {
        return departureAirport + " - " + arrivalAirport + ", Next Departure: " + nextDeparture;
    }
}
