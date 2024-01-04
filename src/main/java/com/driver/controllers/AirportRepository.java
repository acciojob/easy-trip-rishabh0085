package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {

    public AirportRepository() {
    }
    HashMap<String, Airport> airPortDb = new HashMap<>();
    HashMap<Integer, Flight> flightDb = new HashMap<>();
    HashMap<Integer, Passenger> passengerDb = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> flightBookings = new HashMap<>();
    HashMap<Integer, ArrayList<Integer>> passengerBookings = new HashMap<>();

    public void addAirportDetailsToDb(Airport airport) {
        airPortDb.put(airport.getAirportName(), airport);
    }

    public String getLargestAirportInDB() {
        ArrayList<String> airPortList = new ArrayList<>();

        for(String airPortName : airPortDb.keySet()) {
            airPortList.add(airPortName);
        }

        Collections.sort(airPortList);

        String LargestAirport = "";
        int MaxTerminals = Integer.MIN_VALUE;

        for(String airPortName : airPortList) {
            if(airPortDb.containsKey(airPortName)) {
                if(airPortDb.get(airPortName).getNoOfTerminals()>MaxTerminals) {
                    MaxTerminals = airPortDb.get(airPortName).getNoOfTerminals();
                    LargestAirport = airPortName;
                }
            }
        }
        return LargestAirport;
    }

    public void addFlightToDB(Flight flight) {
        flightDb.put(flight.getFlightId(), flight);
    }

    public String addPassengerToDb(Passenger passenger) {
        passengerDb.put(passenger.getPassengerId(), passenger);
        return "SUCCESS";
    }

    public double getShortestDurationOfPossibleBetweenTwoCitiesFromDb(City fromCity, City toCity) {
        double shortestTime = Double.MAX_VALUE;

        for(Flight currFlight : flightDb.values()) {
            if(currFlight.getFromCity().equals(fromCity) && currFlight.getToCity().equals(toCity)) {
                shortestTime = Math.min(shortestTime,currFlight.getDuration());
            }
        }
        if(shortestTime==Double.MAX_VALUE) {
            return -1;
        }
        return shortestTime;
    }

    public String bookFightTicketOnDB(Integer flightId, Integer passengerId) {

        ArrayList<Integer> PassengerList = flightBookings.getOrDefault(flightId, new ArrayList<>());
        ArrayList<Integer> FlightList = passengerBookings.getOrDefault(passengerId, new ArrayList<>());

        if(flightDb.get(flightId).getMaxCapacity()<=PassengerList.size() || PassengerList.contains(passengerId)) {
            return "FAILURE";
        }
        PassengerList.add(passengerId);
        FlightList.add(flightId);

        flightBookings.put(passengerId, PassengerList);
        passengerBookings.put(flightId, FlightList);
        return "SUCCESS";

    }

    public String cancelFlightTicketsOnDB(Integer flightId, Integer passengerId) {

        if(flightBookings.containsKey(flightId)) {
            ArrayList<Integer> PassengerList = flightBookings.get(flightId);
            ArrayList<Integer> FlightList = passengerBookings.getOrDefault(passengerId, new ArrayList<>());
            for(Integer bookedFlight : FlightList) {
                if(bookedFlight == flightId) {
                    FlightList.remove(flightId);
                    PassengerList.remove(passengerId);
                    flightBookings.put(passengerId, PassengerList);
                    passengerBookings.put(flightId, FlightList);
                    return "SUCCESS";
                }
            }
        }
        return "FAILURE";
    }


    public int getNumberOfPeopleOnDate(Date date, String airportName) {
        if(!airPortDb.containsKey(airportName)) {
            return 0;
        }

        City currCity = airPortDb.get(airportName).getCity();

        int countOfPeople = 0;

        for(Integer flightName: flightDb.keySet()) {
            Flight currFlight = flightDb.get(flightName);
            if(currFlight.getFlightDate().equals(date)) {
                if(currFlight.getFromCity().equals(currCity) || currFlight.getToCity().equals(currCity)) {
                    countOfPeople+=flightBookings.get(flightName).size();
                }
            }
        }

        return countOfPeople;

    }

    public int calculateFlightFarePriceFromDB(Integer flightId) {
        int FarePrice = 0;

        if(flightBookings.containsKey(flightId)) {
            int alreadyPeople = flightBookings.get(flightId).size();
            FarePrice += 3000 + alreadyPeople*50;
        }
        else {
            FarePrice += 3000;
        }
        return FarePrice;
    }

    public int getTotalBookingsByPassengerFromDB(Integer passengerId) {
        if(passengerBookings.containsKey(passengerId)) {
            return passengerBookings.get(passengerId).size();
        }
        return 0;
    }

    public String getAirportNameFromTheFlightOnDB(Integer flightId) {
        if(flightDb.containsKey(flightId)) {
            City Takeoffcity = flightDb.get(flightId).getFromCity();
            for(Airport currAirport: airPortDb.values()) {
                if (currAirport.getCity().equals(Takeoffcity)) {
                    return currAirport.getAirportName();
                }
            }
        }
        return null;
    }

    public int calculateFlightRevenueFromDB(Integer flightId) {
        if(flightBookings.containsKey(flightId)) {
            int totalpassengers = flightBookings.get(flightId).size();
            int revenue = 0;
            for(int i=0;i<totalpassengers;i++) {
                revenue+= 3000 + (i*50);
            }
            return revenue;
        }
        return 0;
    }
}