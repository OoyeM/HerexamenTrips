package be.kdg.trips.service;

/**
 * Created by Matthias on 25/07/2015.
 */

import be.kdg.trips.model.Trip;

import java.util.List;


public interface TripService {

    Trip findById(int id);

    void saveTrip(Trip trip);

    void updateTrip(Trip trip);

    void deleteTripById(int id);

    List<Trip> findAllTrips();
}