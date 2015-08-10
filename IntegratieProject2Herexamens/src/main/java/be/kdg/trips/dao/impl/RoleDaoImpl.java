package be.kdg.trips.dao.impl;

import be.kdg.trips.dao.AbstractDao;
import be.kdg.trips.dao.RoleDao;
import be.kdg.trips.model.User;
import be.kdg.trips.model.UserRole;
import org.springframework.stereotype.Repository;

/**
 * Created by Matthias on 5/08/2015.
 */
@Repository("RoleDao")
public class RoleDaoImpl extends AbstractDao<Integer,UserRole> implements RoleDao {
    @Override
    public void create(User user) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole("ROLE_USER");
        persist(userRole);
    }
}
