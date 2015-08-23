package be.kdg.trips.dao.impl;

import be.kdg.trips.dao.AbstractDao;
import be.kdg.trips.dao.TripDao;
import be.kdg.trips.model.Trip;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by Matthias on 24/07/2015.
*/
@Repository("TripDao")
public class TripDaoImpl extends AbstractDao<Integer,Trip> implements TripDao {
    private static final Logger logger = Logger.getLogger(TripDaoImpl.class);
    public Trip findTripById(int id) throws Exception {
        return getByKey(id);
    }



    @Override
    public Long count(Integer offset,Integer limit,String keyWord)throws Exception {
        keyWord=keyWord.toLowerCase();
        Criteria criteria = createEntityCriteria();
        if(keyWord.isEmpty()){
            return (Long)criteria.setProjection(Projections.distinct(Projections.property("tripId")))
                    .setProjection(Projections.rowCount()).uniqueResult();
        }else {
            return (Long) criteria.setProjection(Projections.distinct(Projections.property("tripId")))
                    .createAlias("tripLabels", "labels")
                    .add(Restrictions.disjunction() // OR
                            .add(Restrictions.like("labels.description", "%" + keyWord + "%").ignoreCase())
                            .add(Restrictions.like("title", "%" + keyWord + "%").ignoreCase()))
                    .setProjection(Projections.rowCount()).uniqueResult();
        }
        }

    @Override
    public Long count(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception {
        Criteria criteria = createEntityCriteria();
        keyWord=keyWord.toLowerCase();
        if(keyWord.isEmpty()){
            return (Long)criteria.setProjection(Projections.distinct(Projections.property("tripId")))
                    .add((Restrictions.eq("createdBy.user_id", user_id))).setProjection(Projections.rowCount()).uniqueResult();
        }else {
            return (Long) criteria.setProjection(Projections.distinct(Projections.property("tripId")))
                    .createAlias("tripLabels", "labels")
                    .add(Restrictions.disjunction() // OR
                            .add(Restrictions.like("labels.description", "%" + keyWord + "%").ignoreCase())
                            .add(Restrictions.like("title", "%" + keyWord + "%").ignoreCase()))
                    .add((Restrictions.eq("createdBy.user_id", user_id))).setProjection(Projections.rowCount()).uniqueResult();
        }
    }



    public void saveTrip(Trip employee)throws Exception {
        persist(employee);
    }

    public void deleteTripById(int tripId)throws Exception {
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
        Query deleteEventLocation = getSession().createSQLQuery("delete from Events where TRIP_ID = :tripId");
        deleteEventLocation.setInteger("tripId", tripId);
        deleteEventLocation.executeUpdate();
        Query deleteTrip = getSession().createSQLQuery("delete from trip where TRIP_ID = :tripId");
        deleteTrip.setInteger("tripId", tripId);
        deleteTrip.executeUpdate();
    }

    public List<Trip> findAllTrips(Integer offset,Integer limit,String keyWord)throws Exception {
        keyWord = keyWord.toLowerCase();
        Criteria criteria = createEntityCriteria();
        if(keyWord.isEmpty()){
            return criteria.setFirstResult(offset != null ? offset : 0)
                    .setMaxResults(limit != null ? limit : 10).list();
        }else {

            DetachedCriteria subquery = DetachedCriteria.forClass(Trip.class).
                    setProjection(Projections.distinct(Projections.property("tripId")))
                    .createAlias("tripLabels", "labels")
                    .add(Restrictions.disjunction() // OR
                            .add(Restrictions.like("labels.description", "%" + keyWord + "%").ignoreCase())
                            .add(Restrictions.like("title", "%" + keyWord + "%").ignoreCase()));

            return criteria
                    .add(Subqueries.propertyIn("tripId", subquery))
                    .setFirstResult(offset != null ? offset : 0)
                    .setMaxResults(limit != null ? limit : 10).list();
        }
    }
    @Override
    public List<Trip> findAllTripsByUsername(Integer offset, Integer limit, String keyWord, Integer id)throws Exception {
        keyWord=keyWord.toLowerCase();
        Criteria criteria = createEntityCriteria();
        if(keyWord.isEmpty()){
            return criteria.add((Restrictions.eq("createdBy.user_id", id))).setFirstResult(offset != null ? offset : 0)
                    .setMaxResults(limit != null ? limit : 10).list();
        }else {
            DetachedCriteria subquery = DetachedCriteria.forClass(Trip.class).
                    setProjection(Projections.distinct(Projections.property("tripId")))
                    .createAlias("tripLabels", "labels")
                    .add(Restrictions.disjunction() // OR
                                    .add(Restrictions.like("labels.description", "%" + keyWord + "%").ignoreCase())
                                    .add(Restrictions.like("title", "%" + keyWord + "%").ignoreCase())
                    )
                    .add((Restrictions.eq("createdBy.user_id", id)));

            return criteria
                    .add(Subqueries.propertyIn("tripId", subquery))
                    .setFirstResult(offset != null ? offset : 0)
                    .setMaxResults(limit != null ? limit : 10).list();
        }
    }




}
