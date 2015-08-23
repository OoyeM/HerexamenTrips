package be.kdg.trips.service;

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

import static org.junit.Assert.assertTrue;

/**
 * Created by Matthias on 14/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestUserEventService {

    @Autowired
    UserService userService;
    @Autowired
    UserEventService userEventService;
    @Autowired
    EventService eventService;
    int userId,eventId,userEventId,userId1,userEvent1Id;
    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setUsername("testUser@test.be");
        user.setPassword("test");
        userService.createUser(user);
        userId=user.getUser_id();
        Event event = new Event();
        event.setCreatedBy(user);
        eventService.saveEvent(event);
        eventId = event.getEventId();
        User user2 = new User();
        user2.setUsername("testUser1@test.be");
        user2.setPassword("test");
        userService.createUser(user2);
        userId1=user2.getUser_id();
        UserEvent userEvent = new UserEvent();
        userEvent.setUserId(user2.getUser_id());
        userEvent.setEventId(eventId);
        userEventService.saveUserEvent(userEvent);
        userEventId = userEvent.getUsereventId();
    }

    @Test
    public void updateUserEvent() throws Exception {
        UserEvent event =userEventService.getUserEventByEIdUId(userId1,eventId);
        event.setAccepted(true);
        userEventService.updateUserEvent(userEventService.getUserEventByEIdUId(userId1,eventId));

        assertTrue(userEventService.getUserEventByEIdUId(userId1, eventId).isAccepted());
    }
}
