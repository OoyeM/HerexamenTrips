package be.kdg.trips.service;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.TripImage;
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
 * Created by Matthias on 21/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestTripImageService {
    @Autowired
    UserService userService;
    @Autowired
    TripLocationService tripLocationService;
    @Autowired
    TripImageService tripImageService;
    private int imageId;
    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setUsername("testUser@test.be");
        user.setPassword("test");
        userService.createUser(user);
        TripLocation location = new TripLocation();
        tripLocationService.saveTripLocation(location);
        TripImage image = new TripImage();
        image.setTripLocation(tripLocationService.findTripLocationById(location.getLocationId()));
        image.setDescription("test description");
        tripImageService.saveTripImage(image);
        imageId=image.getImageId();
    }

    @Test
    public void updateImage() throws Exception {
        TripImage image = tripImageService.getTripImageById(imageId);
        image.setDescription("test");
        tripImageService.updateTripImage(image);
        TripImage imageTest = tripImageService.getTripImageById(imageId);
        assertEquals(imageTest.getDescription(),"test");
    }
}
