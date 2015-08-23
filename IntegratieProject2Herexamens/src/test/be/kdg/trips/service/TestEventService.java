package be.kdg.trips.service;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.Event;
import be.kdg.trips.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthias on 21/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestEventService {
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    private int userId,eventId;

    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setUsername("testUser@test.be");
        user.setPassword("test");
        userService.createUser(user);
        userId=user.getUser_id();

    }

    @Test
    public void saveEvent() throws Exception {
        Event event = new Event();
        event.setCreatedBy(userService.getUser("testUser@test.be"));
        eventService.saveEvent(event);
        eventId=event.getEventId();
        Date date = event.getEventDate();
        Event testEvent =eventService.findEventById(eventId);

        assertEquals(date,testEvent.getEventDate());
    }
    @Before
    public void setUpEvent() throws Exception {
        Event event = new Event();
        event.setCreatedBy(userService.getUser("testUser@test.be"));
        event.setTitle("test");
        eventService.saveEvent(event);
        eventId=event.getEventId();
    }
    @Test
    public void update() throws Exception {
        Event event =eventService.findEventById(eventId);
        event.setTitle("testUpdate");
        eventService.updateEvent(event);
        assertEquals(event.getTitle(),"testUpdate");
    }
}
