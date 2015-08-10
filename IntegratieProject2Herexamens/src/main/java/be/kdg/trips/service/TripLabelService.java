package be.kdg.trips.service;

import be.kdg.trips.model.TripLabel;

import java.util.List;

/**
 * Created by Matthias on 5/08/2015.
 */
public interface TripLabelService {

    void saveTrip(TripLabel tripLabel);
    List<TripLabel> findAllTripLabels(Integer tripId);

    void deleteLabel(int labelId);
}
