package be.kdg.trips.dao;

import be.kdg.trips.model.TripLocation;

import java.util.List;

/**
* Created by Matthias on 30/07/2015.
*/
public interface TripLocationDao {
    void saveTripLocation(TripLocation tripLocation) throws Exception;
    void deleteLocationById(int tripLocationId) throws Exception;
    List<TripLocation> findAllLocationsById(int tripId) throws Exception;
    TripLocation findLocationById(int tripLocationId) throws Exception;

    TripLocation findLocationByIdAndOrderNr(int tripId, int orderNumber) throws Exception;
}
