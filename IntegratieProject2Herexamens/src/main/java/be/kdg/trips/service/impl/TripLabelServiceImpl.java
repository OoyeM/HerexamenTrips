package be.kdg.trips.service.impl;

import be.kdg.trips.dao.TripLabelDao;
import be.kdg.trips.model.TripLabel;
import be.kdg.trips.service.TripLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Matthias on 5/08/2015.
 */
@Service("labelService")
@Transactional
public class TripLabelServiceImpl implements TripLabelService {

    @Autowired
    TripLabelDao tripLabelDao;
    @Override
    public void saveTrip(TripLabel tripLabel) {
        tripLabelDao.saveTripLocation(tripLabel);
    }

    @Override
    public List<TripLabel> findAllTripLabels(Integer tripId) {
        return tripLabelDao.findAllLabelsById(tripId);
    }

    @Override
    public void deleteLabel(int labelId) {
        tripLabelDao.deleteLabel(labelId);
    }

}
