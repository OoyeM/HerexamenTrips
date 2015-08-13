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

    List<Event> findAllEvents(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception;

    Long countInvited(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception;

    Long count(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception;

    List<Event> findAllEventsByUsername(Integer offset, Integer limit, String keyWord, Integer id) throws Exception;
}
