package be.kdg.trips.service;

import be.kdg.trips.config.AppConfig;
import be.kdg.trips.model.User;
import be.kdg.trips.model.UserRole;
import be.kdg.trips.service.impl.MyUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthias on 21/08/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestMyUserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;


    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setUsername("testUser@test.be");
        user.setPassword("test");
        userService.createUser(user);
        roleService.createRole(user);
        Set<UserRole> roles = new HashSet<>();
        UserRole role = new UserRole();
        role.setRole("ROLE_USER");
        role.setUser(user);
        roles.add(role);
        user.setUserRole(roles);

    }

    @Test
    public void buildUserForAuthentication() throws Exception {
        Class[] classes = new Class[2];
        classes[0] =be.kdg.trips.model.User.class;
        classes[1]=List.class;
        Method method = MyUserDetailsService.class.getDeclaredMethod("buildUserForAuthentication",classes);
        method.setAccessible(true);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        boolean correctValues=true;
        org.springframework.security.core.userdetails.User user=(org.springframework.security.core.userdetails.User)method.invoke(MyUserDetailsService.class.newInstance(), userService.getUser("testUser@test.be"), authorities);
        if(!user.getUsername().equals("testUser@test.be"))correctValues=false;
        if(!user.getPassword().equals("test"))correctValues=false;
        if(!user.getAuthorities().toArray()[0].equals(authorities.get(0)))correctValues=false;
        assertEquals(true, correctValues);
    }
    @Test
    public void buildUserAuthority() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class[] classes = new Class[1];
        classes[0] = Set.class;
        Set<UserRole> userRoles = new HashSet<>();
        UserRole role = new UserRole();
        role.setRole("ROLE_USER");
        userRoles.add(role);
        Method method = MyUserDetailsService.class.getDeclaredMethod("buildUserAuthority",classes);
        method.setAccessible(true);
        List<GrantedAuthority> list =(List<GrantedAuthority>)method.invoke(MyUserDetailsService.class.newInstance(),userRoles);
        assertEquals(list.get(0).getAuthority(),"ROLE_USER");
    }


    @Test
    public void loadUserByUsername() throws Exception {
//        User test = userService.getUser("testUser2@test.be");
//        Class[] classes = new Class[1];
//        classes[0] = String.class;
//        Method method = MyUserDetailsService.class.getDeclaredMethod("loadUserByUsername",classes);
//        org.springframework.security.core.userdetails.User user=
//                (org.springframework.security.core.userdetails.User)method.invoke(MyUserDetailsService.class.newInstance(), "testUser2@test.be");
        UserDetails user= userService.loadUserByUsername("testUser@test.be");
        boolean correctValues=true;
        if(!user.getUsername().equals("testUser@test.be"))correctValues=false;
        if(!user.getPassword().equals("test"))correctValues=false;
        if(!user.getAuthorities().toArray()[0].toString().equals("ROLE_USER"))correctValues=false;
        assertEquals(true, correctValues);

    }
}
