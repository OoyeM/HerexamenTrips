package be.kdg.trips.service;

import be.kdg.trips.model.TripLocation;

import java.util.List;

/**
* Created by Matthias on 2/08/2015.
*/
public interface TripLocationService {
    List<TripLocation> findAllTripLocations(int tripId) throws Exception;

    void saveTripLocation(TripLocation tripLocation) throws Exception;

    TripLocation findTripLocationById(int id) throws Exception;

    void updateTripLocation(TripLocation tripLocation) throws Exception;

    void updateListPosition(int tripId, int locationId, int command) throws Exception;

    void deleteLocation(int locationId,int tripId) throws Exception;
}
