package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {

    public AirportService() {
    }

    AirportRepository repositoryLayer = new AirportRepository();

    public void addAirportDetails(Airport airport) {
        repositoryLayer.addAirportDetailsToDb(airport);
    }

    public String getLargestTerminalAirport() {
        return repositoryLayer.getLargestAirportInDB();
    }

    public void addFlight(Flight flight) {
        repositoryLayer.addFlightToDB(flight);
    }

    public String addPassenger(Passenger passenger) {
        return repositoryLayer.addPassengerToDb(passenger);
    }

    public double ShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        return repositoryLayer.getShortestDurationOfPossibleBetweenTwoCitiesFromDb(fromCity, toCity);
    }

    public String bookFlightTicket(Integer flightId, Integer passengerId) {
        return repositoryLayer.bookFightTicketOnDB(flightId, passengerId);
    }

    public String cancelFlightTicket(Integer flightId, Integer passengerId) {
        return repositoryLayer.cancelFlightTicketsOnDB(flightId, passengerId);
    }

    public int getNumberOfPeopleOnDate(Date date, String airportName) {
        return repositoryLayer.getNumberOfPeopleOnDate(date, airportName);
    }

    public int calculateFlightFarePrice(Integer flightId) {
        return repositoryLayer.calculateFlightFarePriceFromDB(flightId);
    }

    public int getTotalBookingsByPassenger(Integer passengerId) {
        return repositoryLayer.getTotalBookingsByPassengerFromDB(passengerId);
    }

    public String getAirportNameFromTheFlightId(Integer flightId) {
        return repositoryLayer.getAirportNameFromTheFlightOnDB(flightId);
    }

    public int calculateFlightRevenue(Integer flightId) {
        return repositoryLayer.calculateFlightRevenueFromDB(flightId);
    }
}