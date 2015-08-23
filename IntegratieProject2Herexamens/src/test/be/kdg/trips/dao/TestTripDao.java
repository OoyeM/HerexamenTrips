package be.kdg.trips.dao;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.Trip;
import be.kdg.trips.model.User;
import be.kdg.trips.service.TripService;
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
import static org.junit.Assert.assertTrue;

/**
 * Created by Matthias on 14/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestTripDao {

    @Autowired
    UserDao userDao;

    @Autowired
    TripDao tripDao;

    @Autowired
    TripService tripService;

    int tripId;
    int userId;


//    @Before
//    public void setUp() throws Exception {
//        User user = new User();
//        user.setUsername("testUser@test.be");
//        user.setPassword("test");
//        userDao.saveUser(user);
//        userId=user.getUser_id();
//
//    }

    @Test
    public void saveTrip() throws Exception {
        Trip trip = new Trip();
        trip.setTitle("test");
        trip.setCreatedBy(userDao.findByUserName("testUser@test.be"));
        tripDao.saveTrip(trip);
        tripId =trip.getTripId();
        Trip testtrip =tripDao.findTripById(tripId);

        assertEquals("test",testtrip.getTitle());
    }
    @Before
    public void setUpTrip() throws Exception {
        User user = new User();
        user.setUsername("testUser@test.be");
        user.setPassword("test");
        userDao.saveUser(user);
        Trip trip = new Trip();
        trip.setTitle("test");
        trip.setCreatedBy(userDao.findByUserName("testUser@test.be"));
        tripDao.saveTrip(trip);
        tripId = trip.getTripId();
    }

    @Test
    public void findTrip() throws Exception {
        User user = userDao.findByUserName("testUser@test.be");
        Trip testTrips =tripDao.findTripById(tripId);
        assertEquals("test",testTrips.getTitle());
    }
    @Before
    public void setUpSecondTrip() throws Exception {
        User user = new User();
        user.setUsername("testUser2@test.be");
        user.setPassword("test");
        userDao.saveUser(user);
        Trip trip = new Trip();
        trip.setTitle("test2");
        trip.setCreatedBy(userDao.findByUserName("testUser2@test.be"));
        tripDao.saveTrip(trip);
    }
    @Test
    public void findAllTrips() throws Exception {
        User user = userDao.findByUserName("testUser@test.be");
        List<Trip> trips = tripDao.findAllTrips(0, 10, "");
        boolean test1 = false;
        boolean test2 = false;
        for (Trip t :trips){
            if(t.getCreatedBy().getUsername().equals("testUser@test.be")){
                test1 = true;
            }
            if(t.getCreatedBy().getUsername().equals("testUser2@test.be")){
                test2 = true;
            }
        }
        assertTrue(test1 && test2);

    }
    @Test
    public void countTrips() throws Exception {

        long amount =tripDao.count(0, 10, "");
        assertTrue(amount >= 1);
    }
    @Test
    public void findAllTripsCreatedBy() throws Exception {
        User user = userDao.findByUserName("testUser@test.be");
        userId=user.getUser_id();
        List<Trip> trips = tripDao.findAllTripsByUsername(0, 10, "", user.getUser_id());
        int firstId =trips.get(0).getCreatedBy().getUser_id();
        assertEquals(userId,firstId);
    }
    @Test
    public void countCreatedBy() throws Exception {
        User user = userDao.findByUserName("testUser@test.be");
        long amount =tripDao.count(0, 10, "", user.getUser_id());
        assertEquals(amount,1);
    }

    //
    @Test
    public void deleteTrip()throws Exception{
        tripDao.deleteTripById(tripId);
        long amount =tripDao.count(0, 10, "", userId);
        assertEquals(amount,0);
    }
}
