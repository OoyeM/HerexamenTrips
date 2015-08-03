package be.kdg.trips.dao;

import be.kdg.trips.model.TripLocation;

import java.util.List;

/**
* Created by Matthias on 30/07/2015.
*/
public interface TripLocationDao {
    public void saveTripLocation(TripLocation tripLocation);
    public void deleteLocationById(int tripLocationId);
    public List<TripLocation> findAllLocationsById(int tripId);
    public TripLocation findLocationById(int tripLocationId);
}
