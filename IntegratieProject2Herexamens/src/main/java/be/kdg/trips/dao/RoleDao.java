package be.kdg.trips.dao;

import be.kdg.trips.model.User;

/**
 * Created by Matthias on 5/08/2015.
 */
public interface RoleDao {
    public void create(User user) throws Exception;
}
