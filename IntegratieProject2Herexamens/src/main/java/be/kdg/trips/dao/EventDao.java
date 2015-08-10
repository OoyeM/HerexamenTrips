package be.kdg.trips.dao;

import be.kdg.trips.model.Event;

import java.util.List;

/**
 * Created by Matthias on 9/08/2015.
 */
public interface EventDao {
    Event findEventById(int id);

    void saveEvent(Event event);

    void deleteEventById(int id);

    List<Event> findAllEvents(Integer offset, Integer limit, String keyWord, Integer user_id);

    Long countInvited(Integer offset, Integer limit, String keyWord, Integer user_id);

    Long count(Integer offset, Integer limit, String keyWord, Integer user_id);

    List<Event> findAllEventsByUsername(Integer offset, Integer limit, String keyWord, Integer id);
}
