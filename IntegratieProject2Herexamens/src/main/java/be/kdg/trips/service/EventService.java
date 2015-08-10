package be.kdg.trips.service;

import be.kdg.trips.model.Event;

import java.util.List;

/**
 * Created by Matthias on 9/08/2015.
 */
public interface EventService {
    Event findEventById(int id);

    void saveEvent(Event Event);

    void updateEvent(Event Event);

    void deleteEventById(int id);

    List<Event> findAllEvents(Integer offset,Integer limit,String keyWord, Integer user_id);
    Long countInvitedEvents(Integer offset,Integer limit,String keyWord, Integer user_id);

    List<Event> findAllEventsByUsername(Integer offset, Integer limit, String keyWord, Integer id);

    Long count(Integer offset, Integer limit, String keyWord, Integer user_id);
}
