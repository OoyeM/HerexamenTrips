package be.kdg.trips.dao;

import be.kdg.trips.model.UserEvent;

import java.util.List;

/**
 * Created by Matthias on 10/08/2015.
 */
public interface UserEventDao {

    List<UserEvent> getAllUserEvents(int eventId);
    public void saveUserEvent(UserEvent userEvent);
    public void deleteUserEventById(int userEventId);
    public UserEvent findUserEventById(int userEventId);
}
