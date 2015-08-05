package be.kdg.trips.dao.impl;

import be.kdg.trips.dao.AbstractDao;
import be.kdg.trips.dao.TripImageDao;
import be.kdg.trips.model.TripImage;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by Matthias on 1/08/2015.
*/
@Repository("TripImageDao")
public class TripImageDaoImpl extends AbstractDao<Integer,TripImage> implements TripImageDao {

    @Override
    public void saveTripImage(TripImage tripImage) {
        persist(tripImage);
    }

    @Override
    public void deleteImageById(int tripImageId) {
        Query deleteTripImage = getSession().createSQLQuery("delete from trip_image where IMAGE_ID = :imageId");
        deleteTripImage.setInteger("imageId", tripImageId);
        deleteTripImage.executeUpdate();
    }

    @Override
    public List<TripImage> findAllImagesById(int locationId) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("tripLocation.locationId", locationId));
        return criteria.list();
    }

    @Override
    public TripImage findImageById(int tripImageId) {
        return getByKey(tripImageId);
    }
}
