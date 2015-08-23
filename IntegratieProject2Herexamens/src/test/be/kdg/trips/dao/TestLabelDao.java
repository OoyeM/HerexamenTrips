package be.kdg.trips.dao;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.Trip;
import be.kdg.trips.model.TripLabel;
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
public class TestLabelDao {

    @Autowired
    UserDao userDao;
    @Autowired
    TripDao tripDao;
    @Autowired
    TripLabelDao tripLabelDao;
    private int tripId,label1Id,label2Id,labelId;
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
    public void saveTripLabel() throws Exception {
        TripLabel label = new TripLabel();
        label.setDescription("test");
        label.setTrip(tripDao.findTripById(tripId));
        tripLabelDao.saveTripLabel(label);
        labelId = label.getTripLabelId();
        int localId=0;
        List<TripLabel> loadedLabel =tripLabelDao.findAllLabelsById(tripId);
        for(TripLabel l: loadedLabel){
            if(l.getTripLabelId()==labelId){
                localId=l.getTripLabelId();
            }

        }
        assertEquals(localId,labelId);
    }

    @Before
    public void setUpLabelToLoad() throws Exception {
        TripLabel label1 = new TripLabel();
        label1.setDescription("test1");
        label1.setTrip(tripDao.findTripById(tripId));
        tripLabelDao.saveTripLabel(label1);
        label1Id=label1.getTripLabelId();
        TripLabel label2 = new TripLabel();
        label2.setDescription("test2");
        label2.setTrip(tripDao.findTripById(tripId));
        tripLabelDao.saveTripLabel(label2);
        label2Id=label2.getTripLabelId();
    }

    @Test
    public void getLabels() throws Exception {
        int amount = tripLabelDao.findAllLabelsById(tripId).size();
        assertEquals(amount,2);
    }
    @Test
    public void deleteLabel() throws Exception {
        tripLabelDao.deleteLabel(label1Id);
        assertEquals(tripLabelDao.findAllLabelsById(tripId).size(),1);
    }

}
