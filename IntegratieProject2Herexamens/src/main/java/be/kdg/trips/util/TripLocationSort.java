package be.kdg.trips.util;

import be.kdg.trips.model.TripLocation;
import org.apache.log4j.Logger;

import java.util.Comparator;

/**
 * Created by Matthias on 5/08/2015.
 */
public class TripLocationSort implements Comparator<TripLocation> {
    private static final Logger logger = Logger.getLogger(TripLocationSort.class);
    @Override
    public int compare(TripLocation o1, TripLocation o2) {
        try {
            if (o1.getOrderNumber() < o2.getOrderNumber()) {
                return -1;
            }
            if (o1.getOrderNumber() > o2.getOrderNumber()) {
                return 1;
            } else return 0;
        }catch (Exception e){
            logger.error("error in location sort",e);
            throw e;
        }
    }



}
