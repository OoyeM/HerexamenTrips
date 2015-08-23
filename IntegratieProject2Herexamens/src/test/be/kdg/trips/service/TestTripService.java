package be.kdg.trips.service;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.Trip;
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

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthias on 15/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestTripService {

    @Autowired
    TripService tripService;
    @Autowired
    UserService userService;
    private int tripId;

    @Before
    public void setUpTrip() throws Exception {
        User user = new User();
        user.setUsername("testUser@test.be");
        user.setPassword("test");
        userService.createUser(user);
        Trip trip = new Trip();
        trip.setTitle("test");
        trip.setCreatedBy(userService.getUser("testUser@test.be"));
        tripService.saveTrip(trip);
        tripId = trip.getTripId();
    }

    @Test
    public void updateTrip() throws Exception {
        Trip trip =tripService.findTripById(tripId);
        trip.setTitle("testUpdate");
        tripService.updateTrip(trip);
        assertEquals(trip.getTitle(),"testUpdate");
    }
}
