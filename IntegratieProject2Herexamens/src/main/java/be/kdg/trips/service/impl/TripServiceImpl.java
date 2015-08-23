package be.kdg.trips.service.impl;

/**
* Created by Matthias on 25/07/2015.
*/

import be.kdg.trips.dao.TripDao;
import be.kdg.trips.model.Trip;
import be.kdg.trips.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Transactional which starts a transaction on each method start, and commits it on each method exit
@Service("tripService")
@Transactional
public class TripServiceImpl implements TripService {

    @Autowired
    private TripDao tripDao;

    public Trip findTripById(int id) throws Exception {
        return tripDao.findTripById(id);
    }

    public void saveTrip(Trip trip) throws Exception {
        tripDao.saveTrip(trip);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateTrip(Trip trip) throws Exception {
        Trip entity = tripDao.findTripById(trip.getTripId());
        if (entity != null) {
            entity.setTitle(trip.getTitle());
            entity.setDescription(trip.getDescription());
            entity.setTripLabels(trip.getTripLabels());
        }
    }

    public void deleteTripById(int id) throws Exception {
        tripDao.deleteTripById(id);
    }

    public List<Trip> findAllTrips(Integer offset,Integer limit,String keyWord) throws Exception {
        return tripDao.findAllTrips(offset,limit,keyWord);
    }
    public Long count(Integer offset,Integer limit,String keyWord) throws Exception {
       return tripDao.count(offset,limit,keyWord);
    }
    public Long count(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception {
        return tripDao.count(offset,limit,keyWord,user_id);
    }

    @Override
    public List<Trip> findAllTripsByUsername(Integer offset, Integer limit, String keyWord, Integer id) throws Exception {
        return tripDao.findAllTripsByUsername( offset,  limit,  keyWord,  id);
    }

}
