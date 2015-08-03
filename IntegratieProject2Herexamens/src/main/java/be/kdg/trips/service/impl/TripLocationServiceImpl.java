package be.kdg.trips.service.impl;

import be.kdg.trips.dao.TripLocationDao;
import be.kdg.trips.model.TripLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* Created by Matthias on 2/08/2015.
*/
@Service("tripLocationService")
@Transactional
public class TripLocationServiceImpl implements be.kdg.trips.service.TripLocationService {
    @Autowired
    private TripLocationDao tripLocationDao;
    @Override
    public List<TripLocation> findAllTripLocations(int tripId) {
        return tripLocationDao.findAllLocationsById(tripId);
    }

    @Override
    public void saveTripLocation(TripLocation tripLocation) {
        tripLocationDao.saveTripLocation(tripLocation);
    }

    @Override
    public TripLocation findTripLocationById(int id) {
        return tripLocationDao.findLocationById(id);
    }

    @Override
    public void updateTripLocation(TripLocation tripLocation) {
        TripLocation entity = tripLocationDao.findLocationById(tripLocation.getLocationId());
        if (entity != null) {
            entity.setDescription(tripLocation.getDescription());
            entity.setName(tripLocation.getName());
            entity.setQuestion(tripLocation.getQuestion());
        }

    }
}
