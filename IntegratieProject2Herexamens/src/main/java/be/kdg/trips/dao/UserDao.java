package be.kdg.trips.dao;

import be.kdg.trips.model.User;

import java.util.List;

/**
 * Created by Matthias on 2/08/2015.
 */
public interface UserDao {
    User findByUserName(String login);
    void saveUser(User user);

    List<User> getAllInvitedUsers(int eventId);
}
