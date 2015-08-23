package be.kdg.trips.service;

import be.kdg.trips.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created by Matthias on 5/08/2015.
 */
public interface UserService extends UserDetailsService{
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    public void createUser(be.kdg.trips.model.User user) throws Exception;
    public User getUser(String username) throws Exception;

    List<User> getAllInvitedUsers(int eventId) throws Exception;
}
