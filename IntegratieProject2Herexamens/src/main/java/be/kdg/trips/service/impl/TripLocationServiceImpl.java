package be.kdg.trips.service.impl;

import be.kdg.trips.dao.TripLocationDao;
import be.kdg.trips.model.TripLocation;
import be.kdg.trips.util.LocationCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Matthias on 2/08/2015.
 */
@Service("tripLocationService")
@Transactional
public class TripLocationServiceImpl implements be.kdg.trips.service.TripLocationService {
    @Autowired
    private TripLocationDao tripLocationDao;

    @Override
    public List<TripLocation> findAllTripLocations(int tripId) throws Exception {
        return tripLocationDao.findAllLocationsById(tripId);
    }

    @Override
    public void saveTripLocation(TripLocation tripLocation) throws Exception {
        tripLocationDao.saveTripLocation(tripLocation);
    }

    @Override
    public TripLocation findTripLocationById(int id) throws Exception {
        return tripLocationDao.findLocationById(id);
    }

    @Override
    public void updateTripLocation(TripLocation tripLocation) throws Exception {
        TripLocation entity = tripLocationDao.findLocationById(tripLocation.getLocationId());
        if (entity != null) {
            entity.setDescription(tripLocation.getDescription());
            entity.setName(tripLocation.getName());
            entity.setQuestion(tripLocation.getQuestion());
            entity.setOrderNumber(tripLocation.getOrderNumber());
            entity.setCategory(tripLocation.getCategory());
        }

    }

    @Override
    public void updateListPosition(int tripId, int locationId, int command) throws Exception {
        List<TripLocation> tripLocations = findAllTripLocations(tripId);
        TripLocation locationToUpdate = findTripLocationById(locationId);
        int listSize = tripLocations.size();
        int orderNumber = locationToUpdate.getOrderNumber();
        if (command == 1) {//up
            if (orderNumber == 1) {//do nothing, is already start
            } else   //If second to last, switch ordernumbers and catagories of last and second to last
                if (orderNumber == tripLocations.size()) {
                    TripLocation secondToLast = tripLocationDao.findLocationByIdAndOrderNr(tripId, listSize - 1);
                    TripLocation last = tripLocationDao.findLocationByIdAndOrderNr(tripId, listSize);
                    secondToLast.setOrderNumber(listSize);
                    secondToLast.setCategory(LocationCategories.STOP.getCat());
                    last.setOrderNumber(listSize - 1);
                    last.setCategory(LocationCategories.BETWEEN.getCat());
                    updateTripLocation(secondToLast);
                    updateTripLocation(last);
                } else if (orderNumber == 2) {
                    //If second, switch ordernumber and categories of first and second
                    TripLocation first = tripLocationDao.findLocationByIdAndOrderNr(tripId, 1);
                    TripLocation second = tripLocationDao.findLocationByIdAndOrderNr(tripId, 2);
                    first.setOrderNumber(2);
                    first.setCategory(LocationCategories.BETWEEN.getCat());
                    second.setOrderNumber(1);
                    second.setCategory(LocationCategories.START.getCat());
                    updateTripLocation(first);
                    updateTripLocation(second);
                } else {
                    TripLocation first = tripLocationDao.findLocationByIdAndOrderNr(tripId, orderNumber);
                    TripLocation second = tripLocationDao.findLocationByIdAndOrderNr(tripId, orderNumber - 1);
                    first.setOrderNumber(orderNumber - 1);
                    second.setOrderNumber(orderNumber);
                    updateTripLocation(first);
                    updateTripLocation(second);
                }
        } else if (command == 2) {//down
            if (orderNumber == listSize) {//do nothing, is already Stop
            } else   //If second to last, switch ordernumbers and catagories of last and second to last
                if (orderNumber == tripLocations.size() - 1) {
                    TripLocation secondToLast = tripLocationDao.findLocationByIdAndOrderNr(tripId, listSize - 1);
                    TripLocation last = tripLocationDao.findLocationByIdAndOrderNr(tripId, listSize);
                    secondToLast.setOrderNumber(listSize);
                    secondToLast.setCategory(LocationCategories.STOP.getCat());
                    last.setOrderNumber(listSize - 1);
                    last.setCategory(LocationCategories.BETWEEN.getCat());
                    updateTripLocation(secondToLast);
                    updateTripLocation(last);
                } else if (orderNumber == 1) {
                    //If second, switch ordernumber and categories of first and second
                    TripLocation first = tripLocationDao.findLocationByIdAndOrderNr(tripId, 1);
                    TripLocation second = tripLocationDao.findLocationByIdAndOrderNr(tripId, 2);
                    first.setOrderNumber(2);
                    first.setCategory(LocationCategories.BETWEEN.getCat());
                    second.setOrderNumber(1);
                    second.setCategory(LocationCategories.START.getCat());
                    updateTripLocation(first);
                    updateTripLocation(second);
                } else {
                    TripLocation first = tripLocationDao.findLocationByIdAndOrderNr(tripId, orderNumber);
                    TripLocation second = tripLocationDao.findLocationByIdAndOrderNr(tripId, orderNumber + 1);
                    first.setOrderNumber(orderNumber + 1);
                    second.setOrderNumber(orderNumber);
                    updateTripLocation(first);
                    updateTripLocation(second);
                }
        }
    }

    @Override
    public void deleteLocation(int locationId,int tripId) throws Exception {
        TripLocation toDelete = findTripLocationById(locationId);
        int orderN = toDelete.getOrderNumber();
        tripLocationDao.deleteLocationById(locationId);
        List<TripLocation> tripLocations = findAllTripLocations(tripId);
        int listSize = tripLocations.size();
        int currentOrderNumber = 1;

              for(TripLocation loc : tripLocations){
                  if(currentOrderNumber==1){
                      loc.setCategory(LocationCategories.START.getCat());
                  }
                  if(currentOrderNumber==listSize){
                      loc.setCategory(LocationCategories.STOP.getCat());
                  }else{
                      loc.setCategory(LocationCategories.BETWEEN.getCat());
                  }
                  loc.setOrderNumber(currentOrderNumber);
                  updateTripLocation(loc);
                  currentOrderNumber+=1;
              }

        }


    }

