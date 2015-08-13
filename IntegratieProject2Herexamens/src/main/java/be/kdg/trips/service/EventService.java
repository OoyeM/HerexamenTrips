package be.kdg.trips.service;

import be.kdg.trips.model.Event;

import java.util.List;

/**
 * Created by Matthias on 9/08/2015.
 */
public interface EventService {
    Event findEventById(int id) throws Exception;

    void saveEvent(Event Event) throws Exception;

    void updateEvent(Event Event) throws Exception;

    void deleteEventById(int id) throws Exception;

    List<Event> findAllEvents(Integer offset,Integer limit,String keyWord, Integer user_id) throws Exception;
    Long countInvitedEvents(Integer offset,Integer limit,String keyWord, Integer user_id) throws Exception;

    List<Event> findAllEventsByUsername(Integer offset, Integer limit, String keyWord, Integer id) throws Exception;

    Long count(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception;
}
