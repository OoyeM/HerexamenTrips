package be.kdg.trips.service;

import be.kdg.trips.model.UserEvent;

import java.util.List;

/**
 * Created by Matthias on 10/08/2015.
 */
public interface UserEventService {
    List<UserEvent> getAllUserEvents(int eventId) throws Exception;
    void updateUserEvent(UserEvent Event) throws Exception;

    void saveUserEvent(UserEvent userEvent) throws Exception;


    void removeUserEvent(Integer userId, int eventId) throws Exception;

    UserEvent getUserEventByEIdUId(Integer user_id, int eventId) throws Exception;
}
