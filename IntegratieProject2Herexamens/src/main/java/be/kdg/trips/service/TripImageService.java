package be.kdg.trips.service;

import be.kdg.trips.model.TripImage;

import java.util.List;

/**
* Created by Matthias on 1/08/2015.
*/
public interface TripImageService {
    void saveTripImage(TripImage tripImage);
    TripImage getTripImageById(int imageId);
    void updateTripImage(TripImage tripImage);
    List<TripImage> getAllLocationImages(int tripLocationId);
}
