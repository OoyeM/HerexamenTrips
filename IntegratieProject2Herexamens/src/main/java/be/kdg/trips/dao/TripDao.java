package be.kdg.trips.dao;

import be.kdg.trips.model.Trip;

import java.util.List;

/**
* Created by Matthias on 24/07/2015.
*/
public interface TripDao {
    public void saveTrip(Trip trip);
    public void deleteTripById(int tripId);
    public List<Trip> findAllTrips(Integer offset,Integer limit,String keyWord);
    public Trip findTripById(int tripId);
    public Long count(Integer offset,Integer limit,String keyWord);
}
