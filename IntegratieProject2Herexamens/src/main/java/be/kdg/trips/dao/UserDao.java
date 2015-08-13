package be.kdg.trips.dao;

import be.kdg.trips.model.User;

import java.util.List;

/**
 * Created by Matthias on 2/08/2015.
 */
public interface UserDao {
    User findByUserName(String login) throws Exception;
    void saveUser(User user) throws Exception;

    List<User> getAllInvitedUsers(int eventId) throws Exception;
}
