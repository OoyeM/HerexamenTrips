package be.kdg.trips.controllers;

/**
 * Created by Matthias on 25/07/2015.
 */

import be.kdg.trips.model.*;
import be.kdg.trips.service.*;
import be.kdg.trips.util.TripLocationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;


@Controller
@RequestMapping("/")
@Scope("session")
public class EventController {

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @Autowired
    TripLocationService tripLocationService;

    @Autowired
    TripService tripService;

    @Autowired
    TripImageService tripImageService;

    @Autowired
    TripLabelService tripLabelService;

    @Autowired
    UserEventService userEventService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/events"}, method = RequestMethod.GET)
    public ModelAndView events(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                               @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                               @RequestParam(value = "search", required = false, defaultValue = "") String keyWord
            , ModelMap model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.getUser(userDetail.getUsername());
        String error = (String) request.getSession().getAttribute("error");
        model.addAttribute("error", error);
        request.getSession().removeAttribute("error");
        List<Event> events = eventService.findAllEvents(offset, limit, keyWord,user.getUser_id());
        model.addAttribute("events", events);
        model.addAttribute("count", eventService.countInvitedEvents(offset, limit, keyWord, user.getUser_id()));
        model.addAttribute("offset", offset);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("limit", limit);

        return new ModelAndView("events");
    }
    @RequestMapping(value = {"/events/{eventId}"}, method = RequestMethod.GET)
    public ModelAndView getEvent(@PathVariable int eventId, ModelMap model) {

        Event event = eventService.findEventById(eventId);

        Trip trip = event.getTrip();

        List<TripLocation> tripLocations = tripLocationService.findAllTripLocations(trip.getTripId());
        Collections.sort(tripLocations, new TripLocationSort());
        for (TripLocation t : tripLocations) {
            System.out.print(t.getOrderNumber());
        }
        String labels = "";
        List<TripLabel> tripLabels = tripLabelService.findAllTripLabels(trip.getTripId());
        if (tripLabels.size() != 0) {
            for (TripLabel tl : tripLabels) {
                labels += tl.getDescription() + ", ";
            }
            labels = labels.substring(0, labels.length() - 2);
        } else {
            labels = "No labels found";
        }
        model.addAttribute("event",event);
        model.addAttribute("labels", labels);
        model.addAttribute("tripLocations", tripLocations);
        model.addAttribute("trip", trip);
        return new ModelAndView("eventDetails");

    }



    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents"}, method = RequestMethod.GET)
    public ModelAndView myEvents(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                 @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                 @RequestParam(value = "search", required = false, defaultValue = "") String keyWord
            , ModelMap model, HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.getUser(userDetail.getUsername());
        List<Event> events = eventService.findAllEventsByUsername(offset, limit, keyWord, user.getUser_id());
        Event test = eventService.findEventById(34);
        model.addAttribute("events", events);
        model.addAttribute("count", eventService.count(offset, limit, keyWord, user.getUser_id()));
        model.addAttribute("offset", offset);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("limit", limit);
        return new ModelAndView("myEvents");
    }//CANCEL MAKEN?
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/create"}, method = RequestMethod.GET)
    public ModelAndView createEvent( ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.getUser(userDetail.getUsername());
        Event event = new Event();
        event.setCreatedBy(user);
        eventService.saveEvent(event);
        return new ModelAndView("redirect:/myEvents/create/"+event.getEventId(),model);

    }//CANCEL MAKEN?
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/create/{eventId}"}, method = RequestMethod.GET)
    public ModelAndView saveEvent( ModelMap model,@PathVariable int eventId,HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.getUser(userDetail.getUsername());

        Event event = eventService.findEventById(eventId);
        if(!canEdit(event,request)){
            request.getSession().setAttribute("error", "events can only be added by the event owner.");
            model.clear();
            return new ModelAndView("redirect:/events", model);
        }
        List<User> invitedUsers = userService.getAllInvitedUsers(eventId);
        List<UserEvent> userEvents = userEventService.getAllUserEvents(eventId);
        model.addAttribute("userEvents",userEvents);
        model.addAttribute("invitedUsers",invitedUsers);
        model.addAttribute("event",event);
        return new ModelAndView("createEvent");

    }//CANCEL MAKEN?
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/create/{eventId}/{tripId}"}, method = RequestMethod.POST)
    public ModelAndView updateEvent(@Valid Event event,@PathVariable int tripId,@PathVariable int eventId, ModelMap model,HttpServletRequest request) {
        if(!canEdit(event,request)){
            request.getSession().setAttribute("error", "events can only be added by the event owner.");
            model.clear();
            return new ModelAndView("redirect:/events", model);
        }
        Trip trip = tripService.findTripById(tripId);
        event.setTrip(trip);

        eventService.updateEvent(event);
        return new ModelAndView("/myEvents");

    }
    private boolean canEdit(Event event,HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.getUser(userDetail.getUsername());
        User createdBy=eventService.findEventById(event.getEventId()).getCreatedBy();
        if(!createdBy.getUsername().equals(user.getUsername())){
            return false;
        }else return true;
    }
}
