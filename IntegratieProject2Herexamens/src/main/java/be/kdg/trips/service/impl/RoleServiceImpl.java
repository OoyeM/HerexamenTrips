package be.kdg.trips.service.impl;

import be.kdg.trips.dao.RoleDao;
import be.kdg.trips.model.User;
import be.kdg.trips.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Matthias on 5/08/2015.
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;


    @Override
    public void createRole(User user) {
        roleDao.create(user);
    }

}
