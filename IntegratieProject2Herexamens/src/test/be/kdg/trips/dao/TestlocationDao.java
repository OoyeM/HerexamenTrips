package be.kdg.trips.dao;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.Trip;
import be.kdg.trips.model.TripLocation;
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
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthias on 15/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestlocationDao {

    @Autowired
    UserDao userDao;
    @Autowired
    TripDao tripDao;
    @Autowired
    TripLocationDao tripLocationDao;
    private int tripId,locationId,location1Id,location2Id;
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
    public void saveTripLocation() throws Exception {
        TripLocation location = new TripLocation();
        location.setDescription("test");
        location.setTrip(tripDao.findTripById(tripId));
        tripLocationDao.saveTripLocation(location);
        locationId=location.getLocationId();
        TripLocation loadedLocation = tripLocationDao.findLocationById(locationId);
        assertEquals(loadedLocation.getDescription(),"test");
    }

    @Before
    public void setUpLabelToLoad() throws Exception {
        TripLocation location1 = new TripLocation();
        location1.setDescription("test1");
        location1.setTrip(tripDao.findTripById(tripId));
        location1.setOrderNumber(1);
        tripLocationDao.saveTripLocation(location1);
        location1Id=location1.getLocationId();
        TripLocation location2 = new TripLocation();
        location2.setDescription("test2");
        location2.setOrderNumber(2);
        location2.setTrip(tripDao.findTripById(tripId));
        tripLocationDao.saveTripLocation(location2);
        location2Id=location2.getLocationId();
    }
    @Test
    public void getLocation() throws Exception {
        assertEquals(tripLocationDao.findLocationById(location1Id).getDescription(),"test1");
    }
    @Test
    public void getLocationByOrderNrAndTripId() throws Exception {
        List<TripLocation> test = tripLocationDao.findAllLocationsById(tripId);
        assertEquals(tripLocationDao.findLocationByIdAndOrderNr(tripId,1).getDescription(),"test1");
    }

    @Test
    public void getLocations() throws Exception {
        int amount = tripLocationDao.findAllLocationsById(tripId).size();
        assertEquals(amount,2);
    }

    @Test
    public void deleteLabel() throws Exception {
        tripLocationDao.deleteLocationById(location1Id);
        assertEquals(tripLocationDao.findAllLocationsById(tripId).size(),1);
    }

}
