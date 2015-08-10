package be.kdg.trips.dao.impl;

import be.kdg.trips.dao.AbstractDao;
import be.kdg.trips.dao.UserEventDao;
import be.kdg.trips.model.UserEvent;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Matthias on 10/08/2015.
 */
@Repository("UserEventDao")
public class UserEventDaoImpl extends AbstractDao<Integer,UserEvent> implements UserEventDao{

    @Override
    public List<UserEvent> getAllUserEvents(int eventId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("eventId",eventId));
        return criteria.list();
    }

    @Override
    public void saveUserEvent(UserEvent userEvent) {
        persist(userEvent);
    }

    @Override
    public void deleteUserEventById(int userEventId) {
        Query deleteUserEvent = getSession().createSQLQuery("delete from user_events where TRIP_ID = :userEventId");
        deleteUserEvent.setInteger("userEventId", userEventId);
        deleteUserEvent.executeUpdate();
    }

    @Override
    public UserEvent findUserEventById(int userEventId) {
        return findUserEventById(userEventId);
    }
}
