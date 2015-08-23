package be.kdg.trips.dao;

import be.kdg.trips.model.TripLabel;

import java.util.List;

/**
 * Created by Matthias on 5/08/2015.
 */
public interface TripLabelDao {
    public void saveTripLabel(TripLabel tripLabel) throws Exception;
    public List<TripLabel> findAllLabelsById(int tripId) throws Exception;

    void deleteLabel(int labelId) throws Exception;
}
