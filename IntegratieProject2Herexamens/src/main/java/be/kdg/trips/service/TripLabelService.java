package be.kdg.trips.service;

import be.kdg.trips.model.TripLabel;

import java.util.List;

/**
 * Created by Matthias on 5/08/2015.
 */
public interface TripLabelService {

    void saveTrip(TripLabel tripLabel) throws Exception;
    List<TripLabel> findAllTripLabels(Integer tripId) throws Exception;

    void deleteLabel(int labelId) throws Exception;
}
