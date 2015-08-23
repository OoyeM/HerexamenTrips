package be.kdg.trips.service.impl;

import be.kdg.trips.dao.UserEventDao;
import be.kdg.trips.model.UserEvent;
import be.kdg.trips.service.UserEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Matthias on 10/08/2015.
 */
@Service("userEventService")
@Transactional
public class UserEventServiceImpl implements UserEventService {
    @Autowired
    private UserEventDao userEventDao;

    @Override
    public List<UserEvent> getAllUserEvents(int eventId) throws Exception {
        return userEventDao.getAllUserEvents(eventId);
    }

    @Override
    public void updateUserEvent(UserEvent userEvent) throws Exception {
        be.kdg.trips.model.UserEvent entity = userEventDao.findUserEventById(userEvent.getUsereventId());
        if (entity != null) {
            entity.setAccepted(userEvent.isAccepted());
        }
    }

    @Override
    public void saveUserEvent(UserEvent userEvent) throws Exception {
        userEventDao.saveUserEvent(userEvent);

    }

    @Override
    public void removeUserEvent(Integer userId, int eventId) throws Exception {
        userEventDao.deleteUserEventByUIdEId(userId,eventId);
    }

    @Override
    public UserEvent getUserEventByEIdUId(Integer user_id, int eventId) throws Exception {
        return userEventDao.findUserEventByUIdEId(user_id,eventId);
    }


}
