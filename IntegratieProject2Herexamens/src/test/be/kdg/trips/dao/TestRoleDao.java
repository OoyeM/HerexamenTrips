package be.kdg.trips.dao;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.User;
import be.kdg.trips.model.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthias on 14/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestRoleDao {
    @Autowired
    RoleDao roleDao;
    @Autowired
    UserDao userDao;

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;
    int eventId;
    int userId;


    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setUsername("testUser@test.be");
        user.setPassword("test");
        userDao.saveUser(user);
        userId=user.getUser_id();

    }

    @Test
    public void giveRole() throws Exception {
        User loadedUser =userDao.findByUserName("testUser@test.be");
        roleDao.create(loadedUser);
        UserRole role = roleDao.getRole(loadedUser.getUser_id());
        assertEquals(role.getRole(),"ROLE_USER");
    }



}
