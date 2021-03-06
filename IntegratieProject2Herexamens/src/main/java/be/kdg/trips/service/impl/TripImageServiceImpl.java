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
    public void saveTripImage(TripImage tripImage) throws Exception {
        tripImageDao.saveTripImage(tripImage);
    }

    @Override
    public TripImage getTripImageById(int imageId) throws Exception {
        return tripImageDao.findImageById(imageId);
    }

    @Override
    public void updateTripImage(TripImage tripImage) throws Exception {
        TripImage entity = tripImageDao.findImageById(tripImage.getImageId());
        if (entity != null) {
            entity.setImgUrl(tripImage.getImgUrl());
            entity.setDescription(tripImage.getDescription());
            entity.setThumbUrl(tripImage.getThumbUrl());
        }
    }

    @Override
    public List<TripImage> getAllLocationImages(int tripLocationId) throws Exception {
       return tripImageDao.findAllImagesById(tripLocationId);
    }
}
