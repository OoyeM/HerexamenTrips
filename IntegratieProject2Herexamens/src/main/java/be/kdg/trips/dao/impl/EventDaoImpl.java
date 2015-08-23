package be.kdg.trips.dao.impl;

import be.kdg.trips.dao.AbstractDao;
import be.kdg.trips.dao.EventDao;
import be.kdg.trips.model.Event;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Matthias on 9/08/2015.
 */
@Repository("EventDao")

public class EventDaoImpl extends AbstractDao<Integer, Event> implements EventDao {
    @Override
    public Event findEventById(int id)throws Exception {
        return getByKey(id);
    }

    @Override
    public void saveEvent(Event event)throws Exception {
        persist(event);
    }

    @Override
    public void deleteEventById(int id)throws Exception {
        Query deleteUserEvents = getSession().createSQLQuery("delete from user_events where event_id = :eventId");
        deleteUserEvents.setInteger("eventId", id);
        deleteUserEvents.executeUpdate();
        Query deleteEvent = getSession().createSQLQuery("delete from events where event_id = :eventId");
        deleteEvent.setInteger("eventId", id);
        deleteEvent.executeUpdate();
    }

    @Override
    public List<Event> findAllEventsInvitedFor(Integer offset, Integer limit, String keyWord, Integer user_id)throws Exception {
        keyWord=keyWord.toLowerCase();
        Criteria criteria = createEntityCriteria();
        if(keyWord.isEmpty()){
            DetachedCriteria subquery = DetachedCriteria.forClass(Event.class).setProjection(Projections.distinct(Projections.property("eventId")))
                    .createAlias("userEvents", "userEventsTable")
                    .add(Restrictions.eq("userEventsTable.userId", user_id));

            return (List<Event>)criteria
                    .add(Subqueries.propertyIn("eventId", subquery)).addOrder(Order.asc("eventId"))
                    .setFirstResult(offset != null ? offset : 0)
                    .setMaxResults(limit != null ? limit : 10).list();
        }else {
            DetachedCriteria subquery = DetachedCriteria.forClass(Event.class).setProjection(Projections.distinct(Projections.property("eventId")))
                    .createAlias("userEvents", "userEventsTable")
                    .add(Restrictions.like("title", "%" + keyWord + "%").ignoreCase())
                    .add(Restrictions.eq("userEventsTable.userId", user_id));

            return (List<Event>)criteria
                    .add(Subqueries.propertyIn("eventId", subquery)).addOrder(Order.asc("eventId"))
                    .setFirstResult(offset != null ? offset : 0)
                    .setMaxResults(limit != null ? limit : 10).list();
        }
    }

    @Override
    public Long countInvitedForEvent(Integer offset, Integer limit, String keyWord, Integer user_id)throws Exception {
        Criteria criteria = createEntityCriteria();
        keyWord=keyWord.toLowerCase();
        if(keyWord.isEmpty()){
            return (Long)criteria.setProjection(Projections.distinct(Projections.property("eventId")))
                    .createAlias("userEvents", "userEventsTable")
                    .add(Restrictions.eq("userEventsTable.userId", user_id))
                    .setProjection(Projections.rowCount()).uniqueResult();
        }else {
            return (Long) criteria.setProjection(Projections.distinct(Projections.property("eventId")))
                    .createAlias("userEvents", "userEventsTable")
                    .add(Restrictions.eq("userEventsTable.userId", user_id))
                    .add(Restrictions.like("title", "%" + keyWord + "%").ignoreCase())
                    .setProjection(Projections.rowCount()).uniqueResult();
        }
    }

    @Override
    public Long countMyCreatedEvents(Integer offset, Integer limit, String keyWord, Integer user_id)throws Exception {
        Criteria criteria = createEntityCriteria();
        keyWord=keyWord.toLowerCase();
        if(keyWord.isEmpty()){
            return (Long)criteria.setProjection(Projections.distinct(Projections.property("eventId")))
                    .add((Restrictions.eq("createdBy.user_id", user_id))).setProjection(Projections.rowCount()).uniqueResult();
        }else {
            return (Long) criteria.setProjection(Projections.distinct(Projections.property("eventId")))
                    .add(Restrictions.like("title", "%" + keyWord + "%").ignoreCase())
                    .add((Restrictions.eq("createdBy.user_id", user_id))).setProjection(Projections.rowCount()).uniqueResult();
        }
    }

    @Override
    public List<Event> findAllEventsCreatedByUserId(Integer offset, Integer limit, String keyWord, Integer id)throws Exception {
        keyWord = keyWord.toLowerCase();
        Criteria criteria = createEntityCriteria();
        if (keyWord.isEmpty()) {
            return criteria.add((Restrictions.eq("createdBy.user_id", id))).addOrder(Order.asc("eventId")).setFirstResult(offset != null ? offset : 0)
                    .setMaxResults(limit != null ? limit : 10).list();
        } else {
            return criteria
                    .add((Restrictions.eq("createdBy.user_id", id)))
                    .add(Restrictions.like("title", "%" + keyWord + "%").ignoreCase())
                    .addOrder(Order.asc("eventId"))
                    .setFirstResult(offset != null ? offset : 0)
                    .setMaxResults(limit != null ? limit : 10).setFetchSize(10).list();
        }
    }
}
