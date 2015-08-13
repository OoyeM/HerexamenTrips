package be.kdg.trips.dao;

import be.kdg.trips.model.UserEvent;

import java.util.List;

/**
 * Created by Matthias on 10/08/2015.
 */
public interface UserEventDao {

    List<UserEvent> getAllUserEvents(int eventId) throws Exception;
    public void saveUserEvent(UserEvent userEvent) throws Exception;
    public void deleteUserEventById(int userEventId) throws Exception;
    public UserEvent findUserEventById(int userEventId) throws Exception;

    void deleteUserEventByUIdEId(Integer userId, int eventId) throws Exception;

    UserEvent findUserEventByUIdEId(Integer user_id, int eventId) throws Exception;
}
