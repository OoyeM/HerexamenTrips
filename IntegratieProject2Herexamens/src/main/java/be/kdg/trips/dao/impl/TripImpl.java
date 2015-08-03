package be.kdg.trips.dao.impl;

import be.kdg.trips.dao.AbstractDao;
import be.kdg.trips.dao.TripDao;
import be.kdg.trips.model.Trip;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by Matthias on 24/07/2015.
*/
@Repository("TripDao")
public class TripImpl extends AbstractDao<Integer,Trip> implements TripDao {

    public Trip findTripById(int id) {
        return getByKey(id);
    }


    public void saveTrip(Trip employee) {
        persist(employee);
    }

    public void deleteTripById(int tripId) {
        Query deleteTripLabel = getSession().createSQLQuery("delete from Trip_label where TRIP_ID = :tripId");
        deleteTripLabel.setInteger("tripId", tripId);
        deleteTripLabel.executeUpdate();
        Query deleteLocationImage = getSession().createSQLQuery("delete from Trip_Image where locationId IN(SELECT location_id from TripLocation where TRIP_ID = :tripId)");
        deleteLocationImage.setInteger("tripId", tripId);
        deleteLocationImage.executeUpdate();
        Query deleteTripLocation = getSession().createSQLQuery("delete from TripLocation where TRIP_ID = :tripId");
        deleteTripLocation.setInteger("tripId", tripId);
        deleteTripLocation.executeUpdate();
//        Query deleteUserEventLocation = getSession().createSQLQuery("delete from user_events where event_id IN(SELECT event_id from event where TRIP_ID = :tripId)");
//        deleteUserEventLocation.setInteger("tripId", tripId);
//        deleteUserEventLocation.executeUpdate();
        Query deleteEventLocation = getSession().createSQLQuery("delete from Event where TRIP_ID = :tripId");
        deleteEventLocation.setInteger("tripId", tripId);
        deleteEventLocation.executeUpdate();
        Query deleteTrip = getSession().createSQLQuery("delete from trip where TRIP_ID = :tripId");
        deleteTrip.setInteger("tripId", tripId);
        deleteTrip.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<Trip> findAllTrips() {
        Criteria criteria = createEntityCriteria();
        return criteria.list();
    }


}
