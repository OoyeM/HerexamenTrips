package be.kdg.trips.dao.impl;

import be.kdg.trips.dao.AbstractDao;
import be.kdg.trips.dao.TripLocationDao;
import be.kdg.trips.model.TripLocation;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by Matthias on 30/07/2015.
*/
@Repository("TripLocationDao")
public class TripLocationImpl extends AbstractDao<Integer,TripLocation> implements TripLocationDao {
    @Override
    public void saveTripLocation(TripLocation tripLocation) {
        persist(tripLocation);
    }

    @Override
    public void deleteLocationById(int locationId) {
        Query deleteTripLabel = getSession().createSQLQuery("delete from TripLocation where location_id = :locationId");
        deleteTripLabel.setInteger("locationId", locationId);
        deleteTripLabel.executeUpdate();
    }

    @Override
    public List<TripLocation> findAllLocationsById(int tripId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("trip.tripId", tripId));
        return criteria.list();
    }

    @Override
    public TripLocation findLocationById(int tripLocationId) {
        return getByKey(tripLocationId);
    }
}
