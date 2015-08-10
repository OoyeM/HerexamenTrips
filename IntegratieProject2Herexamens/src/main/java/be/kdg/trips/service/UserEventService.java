package be.kdg.trips.service;

import be.kdg.trips.model.UserEvent;

import java.util.List;

/**
 * Created by Matthias on 10/08/2015.
 */
public interface UserEventService {
    List<UserEvent> getAllUserEvents(int eventId);
    void updateEvent(UserEvent Event);
}
