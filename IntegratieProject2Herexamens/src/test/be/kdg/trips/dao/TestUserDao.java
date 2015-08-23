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

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthias on 14/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestUserDao {

    @Autowired
    UserDao userDao;
        int userId,eventId;
    @Autowired
    UserEventDao userEventDao;
    @Autowired
    EventDao eventDao;


    @Test
    public void saveUser() throws Exception {
        User user = new User();
        user.setUsername("testUser1@test.be");
        user.setPassword("test");
        userDao.saveUser(user);
        userId=user.getUser_id();
        User loadedUser = userDao.findByUserName("testUser1@test.be");
        assertEquals(loadedUser.getUsername(),"testUser1@test.be");
    }
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
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(user.getUser_id());
        userEvent.setEventId(event.getEventId());
        userEventDao.saveUserEvent(userEvent);
    }

    @Test
    public void getUser() throws Exception {
        assertEquals("testUser@test.be",userDao.findByUserName("testUser@test.be").getUsername());
    }

    @Test
    public void getAllInvitedUsers() throws Exception {
        assertEquals(userDao.getAllInvitedUsers(eventId).size(),1);
    }

}
