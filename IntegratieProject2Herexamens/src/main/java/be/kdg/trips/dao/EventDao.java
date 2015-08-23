package be.kdg.trips.dao;

import be.kdg.trips.model.Event;

import java.util.List;

/**
 * Created by Matthias on 9/08/2015.
 */
public interface EventDao {
    Event findEventById(int id) throws Exception;

    void saveEvent(Event event) throws Exception;

    void deleteEventById(int id) throws Exception;

    List<Event> findAllEventsInvitedFor(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception;

    Long countInvitedForEvent(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception;

    Long countMyCreatedEvents(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception;

    List<Event> findAllEventsCreatedByUserId(Integer offset, Integer limit, String keyWord, Integer id) throws Exception;
}
