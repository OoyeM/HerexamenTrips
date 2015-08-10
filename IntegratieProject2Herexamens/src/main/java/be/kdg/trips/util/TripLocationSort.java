package be.kdg.trips.util;

import be.kdg.trips.model.TripLocation;

import java.util.Comparator;

/**
 * Created by Matthias on 5/08/2015.
 */
public class TripLocationSort implements Comparator<TripLocation> {
    @Override
    public int compare(TripLocation o1, TripLocation o2) {
        if(o1.getOrderNumber()<o2.getOrderNumber()){
            return -1;
        }
        if(o1.getOrderNumber()>o2.getOrderNumber()){
            return 1;
        }
        else return 0;
    }



}
