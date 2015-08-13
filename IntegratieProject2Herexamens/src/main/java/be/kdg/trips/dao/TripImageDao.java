package be.kdg.trips.dao;

import be.kdg.trips.model.TripImage;

import java.util.List;

/**
* Created by Matthias on 1/08/2015.
*/
public interface TripImageDao {
    public void saveTripImage(TripImage tripImage) throws Exception;
    public void deleteImageById(int tripImageId) throws Exception;
    public List<TripImage> findAllImagesById(int locationId) throws Exception;
    public TripImage findImageById(int tripImageId) throws Exception;


}
