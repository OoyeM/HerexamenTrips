package be.kdg.trips.service.impl;

import be.kdg.trips.dao.TripImageDao;
import be.kdg.trips.model.TripImage;
import be.kdg.trips.service.TripImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* Created by Matthias on 1/08/2015.
*/
@Service("imageService")
@Transactional
public class TripImageServiceImpl implements TripImageService {
    @Autowired
    TripImageDao tripImageDao;

    @Override
    public void saveTripImage(TripImage tripImage) {
        tripImageDao.saveTripImage(tripImage);
    }

    @Override
    public TripImage getTripImageById(int imageId) {
        return tripImageDao.findImageById(imageId);
    }

    @Override
    public void updateTripImage(TripImage tripImage) {
        TripImage entity = tripImageDao.findImageById(tripImage.getImageId());
        if (entity != null) {
            entity.setImgUrl(tripImage.getImgUrl());
            entity.setDescription(tripImage.getDescription());
            entity.setTripLocation(tripImage.getTripLocation());
            entity.setThumbUrl(tripImage.getThumbUrl());
        }
    }

    @Override
    public List<TripImage> getAllLocationImages(int tripLocationId) {
       return tripImageDao.findAllImagesById(tripLocationId);
    }
}
