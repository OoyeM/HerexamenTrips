package be.kdg.trips.dao.impl;

import be.kdg.trips.dao.AbstractDao;
import be.kdg.trips.dao.TripLabelDao;
import be.kdg.trips.model.TripLabel;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by Matthias on 24/07/2015.
*/
@Repository("TripLabelDao")
public class TripLabelDaoImpl extends AbstractDao<Integer,TripLabel> implements TripLabelDao {

    @Override
    public void saveTripLocation(TripLabel tripLabel)throws Exception {
            persist(tripLabel);
    }

    @Override
    public List<TripLabel> findAllLabelsById(int tripId)throws Exception {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("trip.tripId", tripId));
        return criteria.list();
    }

    @Override
    public void deleteLabel(int labelId)throws Exception {
        Query deleteTripLabel = getSession().createSQLQuery("delete from Trip_label where TRIPLABEL_ID = :labelId");
        deleteTripLabel.setInteger("labelId", labelId);
        deleteTripLabel.executeUpdate();
    }
}
