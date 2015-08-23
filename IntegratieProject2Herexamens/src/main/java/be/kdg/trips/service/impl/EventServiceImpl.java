package be.kdg.trips.service.impl;

import be.kdg.trips.dao.EventDao;
import be.kdg.trips.model.Event;
import be.kdg.trips.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Matthias on 9/08/2015.
 */
@Service("EventService")
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    public Event findEventById(int id) throws Exception {

        return eventDao.findEventById(id);
    }

    public void saveEvent(Event event) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        event.setEventDate(date);
        eventDao.saveEvent(event);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateEvent(Event event) throws Exception {
        Event entity = eventDao.findEventById(event.getEventId());
        if (entity != null) {
            entity.setTitle(event.getTitle());
            entity.setEventDate(event.getEventDate());
            entity.setTrip(event.getTrip());
        }
    }

    public void deleteEventById(int id) throws Exception {
        eventDao.deleteEventById(id);
    }

    public List<Event> findAllEvents(Integer offset,Integer limit,String keyWord, Integer user_id) throws Exception {
        return eventDao.findAllEventsInvitedFor(offset, limit, keyWord, user_id);
    }
    public Long countInvitedEvents(Integer offset,Integer limit,String keyWord, Integer user_id) throws Exception {
        return eventDao.countInvitedForEvent(offset, limit, keyWord, user_id);
    }

    @Override
    public List<Event> findAllEventsByUsername(Integer offset, Integer limit, String keyWord, Integer id) throws Exception {
        return eventDao.findAllEventsCreatedByUserId(offset, limit, keyWord, id);
    }

    public Long count(Integer offset, Integer limit, String keyWord, Integer user_id) throws Exception {
        return eventDao.countMyCreatedEvents(offset, limit, keyWord, user_id);
    }



}
