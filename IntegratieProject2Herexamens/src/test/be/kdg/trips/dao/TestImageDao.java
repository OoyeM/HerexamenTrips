package be.kdg.trips.dao;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.TripImage;
import be.kdg.trips.model.TripLocation;
import be.kdg.trips.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public class TestImageDao {
    @Autowired
    TripLocationDao tripLocationDao;
    @Autowired
    UserDao userDao;
    @Autowired
    TripImageDao tripImageDao;

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;
    int locationId;
    int userId;
    int imageId;



    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setUsername("testUser@test.be");
        user.setPassword("test");
        userDao.saveUser(user);
        userId=user.getUser_id();
        TripLocation location = new TripLocation();
        tripLocationDao.saveTripLocation(location);
        locationId=location.getLocationId();
    }

    @Test
    public void saveTripImage() throws Exception {
        TripImage image = new TripImage();
        image.setTripLocation(tripLocationDao.findLocationById(locationId));
        image.setDescription("test");
        tripImageDao.saveTripImage(image);
        TripImage savedImage =tripImageDao.findAllImagesById(locationId).get(0);
        assertEquals(savedImage.getDescription(), "test");
    }
    @Before
    public void setUpImageToLoad() throws Exception {
        TripImage image = new TripImage();
        image.setTripLocation(tripLocationDao.findLocationById(locationId));
        image.setDescription("test");
        tripImageDao.saveTripImage(image);
        imageId=image.getImageId();
    }
    @Test
    public void getTripImage() throws Exception {
        assertEquals(tripImageDao.findImageById(imageId).getDescription(),"test");
    }
    @Before
    public void setUpSecondImageToLoadList() throws Exception {
        TripImage image = new TripImage();
        image.setTripLocation(tripLocationDao.findLocationById(locationId));
        image.setDescription("test1");
        tripImageDao.saveTripImage(image);
    }
    @Test
    public void getAllTripImagesByLocation() throws Exception {
        List<TripImage> images = tripImageDao.findAllImagesById(locationId);
        assertEquals(images.size(),2);
    }
    @Test
    public void deleteTripImage() throws Exception {
        tripImageDao.deleteImageById(imageId);
       int count = tripImageDao.findAllImagesById(locationId).size();
        assertEquals(1,count);
    }


}
