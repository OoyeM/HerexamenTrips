package be.kdg.trips.service;

import be.kdg.trips.model.User;

import java.util.List;

/**
 * Created by Matthias on 5/08/2015.
 */
public interface UserService {

    public void createUser(be.kdg.trips.model.User user);
    public User getUser(String username);

    List<User> getAllInvitedUsers(int eventId);
}
