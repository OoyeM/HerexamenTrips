package be.kdg.trips.service;

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

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthias on 15/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestlocationService {

    @Autowired
    UserService userService;
    @Autowired
    TripService tripService;
    @Autowired
    TripLocationService tripLocationService;
    private int tripId,location1Id,location2Id,location3Id;
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
        TripLocation location1 = new TripLocation();
        location1.setDescription("test1");
        location1.setTrip(tripService.findTripById(tripId));
        location1.setOrderNumber(1);
        tripLocationService.saveTripLocation(location1);
        location1Id=location1.getLocationId();
        TripLocation location2 = new TripLocation();
        location2.setDescription("test2");
        location2.setOrderNumber(2);
        location2.setTrip(tripService.findTripById(tripId));
        tripLocationService.saveTripLocation(location2);
        location2Id=location2.getLocationId();
        TripLocation location3 = new TripLocation();
        location3.setDescription("test2");
        location3.setOrderNumber(3);
        location3.setTrip(tripService.findTripById(tripId));
        tripLocationService.saveTripLocation(location3);
        location3Id=location3.getLocationId();
    }

    @Test
    public void updateTripLocation() throws Exception {
        TripLocation location = tripLocationService.findTripLocationById(location1Id);
        location.setDescription("testAssert");
        location.setName("testAssert");
        location.setQuestion("testAssert");
        tripLocationService.updateTripLocation(location);
        location = tripLocationService.findTripLocationById(location1Id);
        boolean assertBool = true;
        if(!location.getDescription().equals("testAssert"))assertBool=false;
        if(!location.getQuestion().equals("testAssert"))assertBool=false;
        if(!location.getName().equals("testAssert"))assertBool=false;
        assertEquals(assertBool,true);
    }

    @Test
    public void updateListPosition() throws Exception {
        TripLocation location1 =tripLocationService.findTripLocationById(location1Id);
        TripLocation location2 =tripLocationService.findTripLocationById(location2Id);
        TripLocation location3 =tripLocationService.findTripLocationById(location3Id);
        tripLocationService.updateListPosition(tripId,location1Id,1);
        boolean assertBool = true;
        if(location1.getOrderNumber()!=1||location2.getOrderNumber()!=2||location3.getOrderNumber()!=3)assertBool=false;
        tripLocationService.updateListPosition(tripId,location1Id,2);
        if(location1.getOrderNumber()!=2||location2.getOrderNumber()!=1||location3.getOrderNumber()!=3)assertBool=false;
        tripLocationService.updateListPosition(tripId,location1Id,2);
        if(location1.getOrderNumber()!=3||location2.getOrderNumber()!=1||location3.getOrderNumber()!=2)assertBool=false;
        tripLocationService.updateListPosition(tripId,location1Id,2);
        if(location1.getOrderNumber()!=3||location2.getOrderNumber()!=1||location3.getOrderNumber()!=2)assertBool=false;
        tripLocationService.updateListPosition(tripId,location1Id,1);
        if(location1.getOrderNumber()!=2||location2.getOrderNumber()!=1||location3.getOrderNumber()!=3)assertBool=false;
        tripLocationService.updateListPosition(tripId,location1Id,1);
        if(location1.getOrderNumber()!=1||location2.getOrderNumber()!=2||location3.getOrderNumber()!=3)assertBool=false;
        tripLocationService.updateListPosition(tripId,location1Id,1);
        if(location1.getOrderNumber()!=1||location2.getOrderNumber()!=2||location3.getOrderNumber()!=3)assertBool=false;
        assertEquals(assertBool,true);
    }


}
