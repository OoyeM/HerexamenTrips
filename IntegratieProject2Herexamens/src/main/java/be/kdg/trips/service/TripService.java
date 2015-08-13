package be.kdg.trips.service;

/**
* Created by Matthias on 25/07/2015.
*/

import be.kdg.trips.model.Trip;

import java.util.List;


public interface TripService {

    Trip findTripById(int id) throws Exception;

    void saveTrip(Trip trip) throws Exception;

    void updateTrip(Trip trip) throws Exception;

    void deleteTripById(int id) throws Exception;

    List<Trip> findAllTrips(Integer offset,Integer limit,String keyWord) throws Exception;
    Long count(Integer offset,Integer limit,String keyWord) throws Exception;

    List<Trip> findAllTripsByUsername(Integer offset, Integer limit, String keyWord, Integer id) throws Exception;

    Long count(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception;
}