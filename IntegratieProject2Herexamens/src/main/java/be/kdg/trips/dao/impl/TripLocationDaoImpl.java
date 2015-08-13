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
public class TripLocationDaoImpl extends AbstractDao<Integer,TripLocation> implements TripLocationDao {
    @Override
    public void saveTripLocation(TripLocation tripLocation)throws Exception {
        persist(tripLocation);
    }

    @Override
    public void deleteLocationById(int locationId)throws Exception {
        Query deleteLocationImages = getSession().createSQLQuery("delete from trip_image where locationId = :locationId");
        deleteLocationImages.setInteger("locationId", locationId);
        deleteLocationImages.executeUpdate();
        Query deleteLocation = getSession().createSQLQuery("delete from TripLocation where location_id = :locationId");
        deleteLocation.setInteger("locationId", locationId);
        deleteLocation.executeUpdate();
    }

    @Override
    public List<TripLocation> findAllLocationsById(int tripId)throws Exception {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("trip.tripId", tripId));
        return criteria.list();
    }

    @Override
    public TripLocation findLocationById(int tripLocationId)throws Exception {
        return getByKey(tripLocationId);
    }

    @Override
    public TripLocation findLocationByIdAndOrderNr(int tripId, int orderNumber)throws Exception {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("trip.tripId", tripId))
        .add(Restrictions.eq("orderNumber",orderNumber));
        return (TripLocation)criteria.list().get(0);
    }
}
