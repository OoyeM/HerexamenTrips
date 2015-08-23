package be.kdg.trips.util;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.TripLocation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
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
public class TestTripLocationSort {

    @Test
    public void sortList(){
        List<TripLocation> locations = new ArrayList<>();
        for(int i=0;i<10;i++) {
            TripLocation location = new TripLocation();
            location.setOrderNumber(9-i);
            locations.add(location);
        }
        Collections.sort(locations,new TripLocationSort());

        assertEquals(locations.get(9).getOrderNumber(),9);
    }

}
