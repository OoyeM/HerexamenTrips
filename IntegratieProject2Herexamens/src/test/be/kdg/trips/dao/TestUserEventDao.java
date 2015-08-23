package be.kdg.trips.dao;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.Event;
import be.kdg.trips.model.User;
import be.kdg.trips.model.UserEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthias on 14/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestUserEventDao {

    @Autowired
    UserDao userDao;
        int userId,eventId,userEventId,userId1,userEvent1Id;
    @Autowired
    UserEventDao userEventDao;
    @Autowired
    EventDao eventDao;
    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setUsername("testUser@test.be");
        user.setPassword("test");
        userDao.saveUser(user);
        userId=user.getUser_id();
        Event event = new Event();
        event.setCreatedBy(user);
        eventDao.saveEvent(event);
        eventId = event.getEventId();
        User user2 = new User();
        user2.setUsername("testUser1@test.be");
        user2.setPassword("test");
        userDao.saveUser(user2);
        userId1=user2.getUser_id();
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(user2.getUser_id());
        userEvent.setEventId(eventId);
        userEventDao.saveUserEvent(userEvent);
        userEventId = userEvent.getUsereventId();
    }

    @Test
    public void saveUserEvent() throws Exception {
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(userId);
        userEvent.setEventId(eventId);
        userEventDao.saveUserEvent(userEvent);


        assertEquals(userEventDao.findUserEventById(userEvent.getUsereventId()).getUsereventId(),userEvent.getUsereventId());
    }



    @Test
    public void getUserEvent() throws Exception {

        int localId =userEventDao.findUserEventByUIdEId(userId1,eventId).getUsereventId();
        assertEquals(localId,userEventId);
    }


    @Test
    public void getAllInvitedUsers() throws Exception {
        assertEquals(userEventDao.getAllUserEvents(eventId).size(),1);
    }
    @Test
    public void deleteUserEvent() throws Exception {
        List<UserEvent> test =userEventDao.getAllUserEvents(eventId);
        userEventDao.deleteUserEventById(userEventId);
        test =userEventDao.getAllUserEvents(eventId);
        assertEquals(userEventDao.getAllUserEvents(eventId).size(),0);
    }

}
