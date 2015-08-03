package be.kdg.trips.service;

import be.kdg.trips.model.TripLocation;

import java.util.List;

/**
* Created by Matthias on 2/08/2015.
*/
public interface TripLocationService {
    List<TripLocation> findAllTripLocations(int tripId);

    void saveTripLocation(TripLocation tripLocation);

    TripLocation findTripLocationById(int id);

    void updateTripLocation(TripLocation tripLocation);
}
