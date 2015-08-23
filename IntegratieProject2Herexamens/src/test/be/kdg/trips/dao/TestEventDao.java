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
public class TestEventDao {
    @Autowired
    EventDao eventDao;
    @Autowired
    UserDao userDao;
    @Autowired
    UserEventDao userEventDao;


    int eventId;
    int userId;


    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setUsername("testUser@test.be");
        user.setPassword("test");
        userDao.saveUser(user);
        userId=user.getUser_id();

    }

    @Test
    public void saveEvent() throws Exception {
        Event event = new Event();
        event.setCreatedBy(userDao.findByUserName("testUser@test.be"));
        event.setTitle("test");
        eventDao.saveEvent(event);
        eventId=event.getEventId();
        Event testEvent =eventDao.findEventById(eventId);

        assertEquals("test",testEvent.getTitle());
    }
    @Before
    public void setUpEvent() throws Exception {
        Event event = new Event();
        event.setCreatedBy(userDao.findByUserName("testUser@test.be"));
        event.setTitle("test");
        eventDao.saveEvent(event);
        eventId=event.getEventId();
        UserEvent userEvent =new UserEvent();
        userEvent.setAccepted(true);
        userEvent.setUserId(userDao.findByUserName("testUser@test.be").getUser_id());
        userEvent.setEventId(event.getEventId());
        userEventDao.saveUserEvent(userEvent);

    }

    @Test
    public void findEvent() throws Exception {
        User user = userDao.findByUserName("testUser@test.be");
        Event testEvent =eventDao.findEventById(eventId);
        assertEquals("test",testEvent.getTitle());
    }
    @Test
    public void findAllEventsInvitedFor() throws Exception {
        User user = userDao.findByUserName("testUser@test.be");
        List<Event> events = eventDao.findAllEventsInvitedFor(0, 10, "", userId);
        int firstId =events.get(0).getCreatedBy().getUser_id();
        assertEquals(userId,firstId);
    }
    @Test
    public void countInvitedFor() throws Exception {

        long amount =eventDao.countInvitedForEvent(0, 10, "", userId);
        assertEquals(amount,1);
    }
    @Test
    public void findAllEventsCreatedBy() throws Exception {
        User user = userDao.findByUserName("testUser@test.be");
        List<Event> events = eventDao.findAllEventsCreatedByUserId(0, 10, "", user.getUser_id());
        int firstId =events.get(0).getCreatedBy().getUser_id();
        assertEquals(userId,firstId);
    }
    @Test
    public void countCreatedBy() throws Exception {
        long amount =eventDao.countMyCreatedEvents(0, 10, "", userId);
        assertEquals(amount,1);
    }

//
    @Test
    public void deleteEvent()throws Exception{
        eventDao.deleteEventById(eventId);
        long amount =eventDao.countMyCreatedEvents(0, 10, "", userId);
        assertEquals(amount,0);
    }


}
