package be.kdg.trips.service;

import be.kdg.trips.model.TripImage;

import java.util.List;

/**
* Created by Matthias on 1/08/2015.
*/
public interface TripImageService {
    void saveTripImage(TripImage tripImage) throws Exception;
    TripImage getTripImageById(int imageId) throws Exception;
    void updateTripImage(TripImage tripImage) throws Exception;
    List<TripImage> getAllLocationImages(int tripLocationId) throws Exception;
}
