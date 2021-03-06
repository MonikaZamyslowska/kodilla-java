package com.kodilla.good.patterns.fly.challenge;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightSearch {

    private static final int ZERO = 0;
    Flights flights = new Flights();

    public void findFlightFrom(String departureCity) {
        List<Flight> flightsfrom = flights.getFlightsList().stream()
                .filter(flight -> flight.getDepartureAirport().equals(departureCity))
                .collect(Collectors.toList());
        if (flightsfrom.size() > ZERO) {
            System.out.println("\nFlights available from: " + departureCity);
            flightsfrom.stream()
                    .forEach(System.out::println);
        } else {
            System.out.println("\nSorry, flights From " + departureCity + " are currently unavailable");
        }
    }

    public void findFlightTo(String arrivalCity) {
        List<Flight> flightsTo = flights.getFlightsList().stream()
                .filter(flight -> flight.getArrivalAirport().equals(arrivalCity))
                .collect(Collectors.toList());
        if (flightsTo.size() > ZERO) {
            System.out.println("\nFlights available to: " + arrivalCity);
            flightsTo.forEach(System.out::println);
        } else {
            System.out.println("\nSorry, flights to: " + arrivalCity + " are currently unavailable");
        }
    }

    public void findConnectingFlight(String departureCity, String transferCity, String arrivalCity) {
        Optional<Flight> fromDepartureToTransfer = flights.getFlightsList().stream()
                .filter(flight -> flight.getDepartureAirport().equals(departureCity))
                .filter(flight -> flight.getArrivalAirport().equals(transferCity))
                .findAny();

        Optional<Flight> fromTransferToArrival = flights.getFlightsList().stream()
                .filter(flights -> flights.getDepartureAirport().equals(transferCity))
                .filter(flights -> flights.getArrivalAirport().equals(arrivalCity))
                .findAny();
        if (fromDepartureToTransfer.isPresent() && fromTransferToArrival.isPresent()) {
            System.out.println("\nConnecting flight: " + departureCity + " - " + transferCity + " - " + arrivalCity +
                    " is AVAILABLE");
        } else {
            System.out.println("\nConnecting flight: " + departureCity + " - " + transferCity + " - " + arrivalCity +
                    " is UNAVAILABLE");
        }
    }

}
