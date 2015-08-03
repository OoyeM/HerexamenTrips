package be.kdg.trips.service.impl;

/**
* Created by Matthias on 25/07/2015.
*/

import be.kdg.trips.dao.TripDao;
import be.kdg.trips.dao.TripLocationDao;
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

    @Autowired
    TripLocationDao tripLocationDao;

    public Trip findTripById(int id) {
        return tripDao.findTripById(id);
    }

    public void saveTrip(Trip trip) {
        tripDao.saveTrip(trip);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateTrip(Trip trip) {
        Trip entity = tripDao.findTripById(trip.getTripId());
        if (entity != null) {
            entity.setTitle(trip.getTitle());
            entity.setDescription(trip.getDescription());
            entity.setTripLabels(trip.getTripLabels());
//            entity.setEvents(trip.getEvents());
        }
    }

    public void deleteTripById(int id) {
        tripDao.deleteTripById(id);
    }

    public List<Trip> findAllTrips() {
        return tripDao.findAllTrips();
    }


}
