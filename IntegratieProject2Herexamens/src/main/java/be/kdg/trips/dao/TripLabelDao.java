package be.kdg.trips.dao;

import be.kdg.trips.model.TripLabel;

import java.util.List;

/**
 * Created by Matthias on 5/08/2015.
 */
public interface TripLabelDao {
    public void saveTripLocation(TripLabel tripLabel);
    public List<TripLabel> findAllLabelsById(int tripId);

    void deleteLabel(int labelId);
}
