package be.kdg.trips.dao;

import be.kdg.trips.model.TripImage;

import java.util.List;

/**
* Created by Matthias on 1/08/2015.
*/
public interface TripImageDao {
    public void saveTripImage(TripImage tripImage);
    public void deleteImageById(int tripImageId);
    public List<TripImage> findAllImagesById(int locationId);
    public TripImage findImageById(int tripImageId);


}
