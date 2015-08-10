package be.kdg.trips.service;

/**
* Created by Matthias on 25/07/2015.
*/

import be.kdg.trips.model.Trip;

import java.util.List;


public interface TripService {

    Trip findTripById(int id);

    void saveTrip(Trip trip);

    void updateTrip(Trip trip);

    void deleteTripById(int id);

    List<Trip> findAllTrips(Integer offset,Integer limit,String keyWord);
    Long count(Integer offset,Integer limit,String keyWord);

    List<Trip> findAllTripsByUsername(Integer offset, Integer limit, String keyWord, Integer id);

    Long count(Integer offset, Integer limit, String keyWord, Integer user_id);
}