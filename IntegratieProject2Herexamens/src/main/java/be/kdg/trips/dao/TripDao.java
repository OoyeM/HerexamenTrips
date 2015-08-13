package be.kdg.trips.dao;

import be.kdg.trips.model.Trip;

import java.util.List;

/**
* Created by Matthias on 24/07/2015.
*/
public interface TripDao {
    public void saveTrip(Trip trip)throws Exception;
    public void deleteTripById(int tripId)throws Exception;
    public List<Trip> findAllTrips(Integer offset,Integer limit,String keyWord)throws Exception;
    public Trip findTripById(int tripId) throws Exception;
    public Long count(Integer offset,Integer limit,String keyWord) throws Exception;

    List<Trip> findAllTripsByUsername(Integer offset, Integer limit, String keyWord, Integer id)throws Exception;

    Long count(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception;
}
