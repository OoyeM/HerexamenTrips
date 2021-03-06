package be.kdg.trips.dao.impl;

import be.kdg.trips.dao.AbstractDao;
import be.kdg.trips.dao.UserDao;
import be.kdg.trips.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Matthias on 2/08/2015.
 */
@Repository("UserDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {


    @Override
    public User findByUserName(String login)throws Exception {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("username", login));
        return (User) criteria.list().get(0);
    }

    @Override
    public void saveUser(User user)throws Exception {
        persist(user);
    }

    @Override
    public List<User> getAllInvitedUsers(int eventId) throws Exception{
        Criteria criteria = createEntityCriteria();

        return criteria.createAlias("invitedEvents", "userEvents").
                add(Restrictions.eq("userEvents.eventId", eventId)).list();
    }
}
