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

    @Autowired
    private EmailHelperService emailHelperService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/events"}, method = RequestMethod.GET)
    public ModelAndView events(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                 @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                 @RequestParam(value = "search", required = false, defaultValue = "") String keyWord
            , ModelMap model, HttpServletRequest request) throws Exception {
        User user = getUser();
        List<Event> events = eventService.findAllEvents(offset, limit, keyWord, user.getUser_id());
        model.addAttribute("events", events);
        model.addAttribute("count", eventService.countInvitedEvents(offset, limit, keyWord, user.getUser_id()));
        model.addAttribute("offset", offset);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("limit", limit);
        String error = (String) request.getSession().getAttribute("error");
        model.addAttribute("error", error);
        request.getSession().removeAttribute("error");
        return new ModelAndView("events");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/events/{eventId}"}, method = RequestMethod.GET)
    public ModelAndView eventDetail(ModelMap model, @PathVariable int eventId, HttpServletRequest request) throws Exception {
        User user = getUser();
        Event event = eventService.findEventById(eventId);
        List<User> invitedUsers = userService.getAllInvitedUsers(eventId);
        for (User u : invitedUsers) {
            if (user.getUsername().equals(u.getUsername())) {
                String error = (String) request.getSession().getAttribute("error");
                model.addAttribute("error", error);
                request.getSession().removeAttribute("error");
                List<UserEvent> userEvents = userEventService.getAllUserEvents(eventId);
                User newUser = new User();
                newUser.setPassword("validation");
                model.addAttribute("user", newUser);
                model.addAttribute("userEvents", userEvents);
                model.addAttribute("invitedUsers", invitedUsers);
                model.addAttribute("event", event);
                return new ModelAndView("eventDetails");
            }
        }
        String error = (String) request.getSession().getAttribute("error");
        model.addAttribute("error", error);
        request.getSession().removeAttribute("error");
        request.getSession().setAttribute("error", "events can only be viewed by invited users");
        model.clear();
        return new ModelAndView("redirect:/events", model);

    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/events/event/{eventId}/{tripId}"}, method = RequestMethod.GET)
    public ModelAndView getTrip(@PathVariable int eventId,@PathVariable int tripId, ModelMap model,HttpServletRequest request) throws Exception {
        User user = getUser();
        Event event = eventService.findEventById(eventId);
        List<User> invitedUsers = userService.getAllInvitedUsers(eventId);
        for (User u : invitedUsers) {
            if (user.getUsername().equals(u.getUsername())) {
                Trip trip = tripService.findTripById(tripId);
                List<TripLocation> tripLocations = tripLocationService.findAllTripLocations(tripId);
                Collections.sort(tripLocations, new TripLocationSort());
                for (TripLocation t : tripLocations) {
                    System.out.print(t.getOrderNumber());
                }
                String labels = "";
                List<TripLabel> tripLabels = tripLabelService.findAllTripLabels(tripId);
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


                return new ModelAndView("eventTrip");
            }
        }
        request.getSession().setAttribute("error", "events can only be viewed by invited users.");
        model.clear();
        return new ModelAndView("redirect:/events", model);

    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/{eventId}/eventLocations/{tripId}/{tripLocationId}"}, method = RequestMethod.GET)
    public ModelAndView LocationDetails(@PathVariable int eventId,@PathVariable int tripId, @PathVariable int tripLocationId, ModelMap model) throws Exception {
        TripLocation tripLocation = tripLocationService.findTripLocationById(tripLocationId);
        model.addAttribute("tripLocation", tripLocation);
        model.addAttribute("eventId",eventId);
        model.addAttribute("tripId", tripId);
        return new ModelAndView("eventLocationDetails");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents"}, method = RequestMethod.GET)
    public ModelAndView myEvents(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                 @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                 @RequestParam(value = "search", required = false, defaultValue = "") String keyWord
            , ModelMap model, HttpServletRequest request) throws Exception {
        User user = getUser();
        List<Event> events = eventService.findAllEventsByUsername(offset, limit, keyWord, user.getUser_id());
        model.addAttribute("events", events);
        model.addAttribute("count", eventService.count(offset, limit, keyWord, user.getUser_id()));
        model.addAttribute("offset", offset);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("limit", limit);
        return new ModelAndView("myEvents");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/create"}, method = RequestMethod.GET)
    public ModelAndView createEvent(ModelMap model) throws Exception {
        User user = getUser();
        Event event = new Event();
        event.setCreatedBy(user);
        eventService.saveEvent(event);
        UserEvent userEvent = new UserEvent();
        userEvent.setAccepted(true);
        userEvent.setEventId(event.getEventId());
        userEvent.setUserId(user.getUser_id());
        userEventService.saveUserEventService(userEvent);
        return new ModelAndView("redirect:/myEvents/create/" + event.getEventId(), model);

    }//CANCEL MAKEN?

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/create/{eventId}"}, method = RequestMethod.GET)
    public ModelAndView saveEvent(ModelMap model, @PathVariable int eventId, HttpServletRequest request) throws Exception {

        User user = getUser();

        Event event = eventService.findEventById(eventId);
        if(event.getTrip()!=null){
            return new ModelAndView("redirect:/myEvents/create/" + eventId + "/" + event.getTrip().getTripId());

        }
        if (!canEdit(event, request)) {
            request.getSession().setAttribute("error", "events can only be added by the event owner.");
            model.clear();
            return new ModelAndView("redirect:/events", model);
        }
        String error = (String) request.getSession().getAttribute("error");
        model.addAttribute("error", error);
        request.getSession().removeAttribute("error");
        List<User> invitedUsers = userService.getAllInvitedUsers(eventId);
        List<UserEvent> userEvents = userEventService.getAllUserEvents(eventId);
        User newUser = new User();
        newUser.setPassword("validation");
        model.addAttribute("user", newUser);
        model.addAttribute("userEvents", userEvents);
        model.addAttribute("invitedUsers", invitedUsers);
        model.addAttribute("event", event);
        return new ModelAndView("createEvent");

    }//CANCEL MAKEN?

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/create/{eventId}/{tripId}"}, method = RequestMethod.GET)
    public ModelAndView viewEvent(@PathVariable int tripId, @PathVariable int eventId, ModelMap model, HttpServletRequest request) throws Exception {

        List<User> invitedUsers = userService.getAllInvitedUsers(eventId);
        Event event = eventService.findEventById(eventId);
        User user = getUser();
        for (User u : invitedUsers) {
            if (user.getUsername().equals(u.getUsername())) {
                String error = (String) request.getSession().getAttribute("error");
                model.addAttribute("error", error);
                request.getSession().removeAttribute("error");
                User newUser = new User();
                newUser.setPassword("validation");
                List<UserEvent> userEvents = userEventService.getAllUserEvents(eventId);
                model.addAttribute("userEvents", userEvents);
                model.addAttribute("invitedUsers", invitedUsers);
                model.addAttribute("event", event);
                model.addAttribute("user", newUser);
                return new ModelAndView("createEvent");
            }
        }
        String error = (String) request.getSession().getAttribute("error");
        model.addAttribute("error", error);
        request.getSession().removeAttribute("error");
        request.getSession().setAttribute("error", "events can only be viewed by invited users");
        model.clear();
        return new ModelAndView("redirect:/events", model);



    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/create/{eventId}"}, method = RequestMethod.POST)
    public ModelAndView updateEventNoTripId(@Valid Event event, @PathVariable int eventId, ModelMap model, HttpServletRequest request) throws Exception {
        if (!canEdit(event, request)) {
            request.getSession().setAttribute("error", "events can only be added by the event owner.");
            model.clear();
            return new ModelAndView("redirect:/events", model);
        }
        eventService.updateEvent(event);
        return new ModelAndView("redirect:/myEvents/create/"+eventId);

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/create/{eventId}/{tripId}"}, method = RequestMethod.POST)
    public ModelAndView updateEvent(@Valid Event event, @PathVariable int tripId, @PathVariable int eventId, ModelMap model, HttpServletRequest request) throws Exception {
        if (!canEdit(event, request)) {
            request.getSession().setAttribute("error", "events can only be added by the event owner.");
            model.clear();
            return new ModelAndView("redirect:/events", model);
        }
        Trip trip = tripService.findTripById(tripId);
        event.setTrip(trip);

        eventService.updateEvent(event);
        return new ModelAndView("/myEvents");

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/{eventId}/addTripToEvent/"}, method = RequestMethod.POST)
    public ModelAndView addTripToUserEvent(@RequestParam int addedTripId, @PathVariable int eventId, ModelMap model, HttpServletRequest request) throws Exception {

        List<User> invitedUsers = userService.getAllInvitedUsers(eventId);
        Event event = eventService.findEventById(eventId);
        if(event.getCreatedBy().getUsername().equals(getUser().getUsername())){
            event.setTrip(tripService.findTripById(addedTripId));
            eventService.updateEvent(event);
            return new ModelAndView("redirect:/myEvents/create/" + eventId);
        }

        request.getSession().setAttribute("error", "Only creator can add a trip to an event.");
        model.clear();
        return new ModelAndView("redirect:/myEvents/create/" + eventId);

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/create/{eventId}/addUser"}, method = RequestMethod.POST)
    public ModelAndView addInvitedUser(@RequestParam String username, @PathVariable int eventId, ModelMap model, HttpServletRequest request) throws Exception {

        List<User> invitedUsers = userService.getAllInvitedUsers(eventId);
        Event event = eventService.findEventById(eventId);

        for (User u : invitedUsers) {
            if (getUser().getUsername().equals(u.getUsername())) {
                UserEvent userEvent = new UserEvent();
                userEvent.setAccepted(false);
                userEvent.setEventId(eventId);
                try {
                    User addedUser = userService.getUser(username);
                    userEvent.setUserId(addedUser.getUser_id());

                    userEventService.saveUserEventService(userEvent);
                    //REAL APP CHANGE THIS FIRST PARAMETER INTO @username
                    emailHelperService.sendEmail("matthias.ooye@outlook.com", "Invitation for event on: "+event.getEventDate(),
                            "Go to the following link to accept/reject:    \n" +
                                    " http://localhost:8080/Trips/myEvents/alter/" + eventId + "/acceptInvite/" + username + "/");
                    if(!getUser().getUsername().equals(event.getCreatedBy().getUsername())){
                        return new ModelAndView("redirect:/events/"+eventId);
                    }
                    if (event.getTrip() == null) {
                        return new ModelAndView("redirect:/myEvents/create/" + eventId);
                    } else
                        return new ModelAndView("redirect:/myEvents/create/" + eventId + "/" + event.getTrip().getTripId());
                } catch (Exception e) {
                    request.getSession().setAttribute("error", "User not found");
                    model.clear();
                    if (event.getTrip() == null) {
                        return new ModelAndView("redirect:/myEvents/create/" + eventId);
                    } else
                        return new ModelAndView("redirect:/myEvents/create/" + eventId + "/" + event.getTrip().getTripId());
                }

            }
        }
        request.getSession().setAttribute("error", "Only invited users can invite others to join, or view this page.");
        model.clear();
        return new ModelAndView("redirect:/myEvents/");

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/alter/{eventId}/acceptInvite/{username}/"}, method = RequestMethod.GET)
    public ModelAndView acceptInvite(@PathVariable String username, @PathVariable int eventId, ModelMap model, HttpServletRequest request) throws Exception {

        List<User> invitedUsers = userService.getAllInvitedUsers(eventId);
        Event event = eventService.findEventById(eventId);
        for (User u : invitedUsers) {
            if (getUser().getUsername().equals(u.getUsername())) {
                if(getUser().getUsername().equals(username)){
                    model.addAttribute("event",event);
                    return new ModelAndView("acceptReject");
                }
            }
        }

        request.getSession().setAttribute("error", "Only the invited user can accept the invite.");
        model.clear();
        return new ModelAndView("redirect:/myEvents/");

    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/alter/{eventId}/acceptInvite/{username}/{accepted}"}, method = RequestMethod.GET)
    public ModelAndView saveInvite(@PathVariable String username, @PathVariable int eventId,@PathVariable boolean accepted, ModelMap model, HttpServletRequest request) throws Exception {

        List<User> invitedUsers = userService.getAllInvitedUsers(eventId);
        Event event = eventService.findEventById(eventId);
        for (User u : invitedUsers) {
            if (getUser().getUsername().equals(u.getUsername())) {
                if(getUser().getUsername().equals(username)){
                   UserEvent userEvent= userEventService.getUserEventByEIdUId(u.getUser_id(),eventId);
                    userEvent.setAccepted(accepted);
                   userEventService.updateEvent(userEvent);
                    if(getUser().getUsername().equals(event.getCreatedBy().getUsername())){
                        return new ModelAndView("redirect:/myEvents/");
                    }else return new ModelAndView("redirect:/events/"+eventId);
                }
            }
        }

        request.getSession().setAttribute("error", "Only the invited user can accept the invite.");
        model.clear();
        return new ModelAndView("redirect:/myEvents/");

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myEvents/create/{eventId}/removeUser/{username}/"}, method = RequestMethod.GET)
    public ModelAndView removeInvitedUser(@PathVariable String username, @PathVariable int eventId, ModelMap model, HttpServletRequest request) throws Exception {

        List<User> invitedUsers = userService.getAllInvitedUsers(eventId);
        Event event = eventService.findEventById(eventId);
        for (User u : invitedUsers) {
            if (getUser().getUsername().equals(u.getUsername())) {
                if (!event.getCreatedBy().getUsername().equals(username)) {
                    userEventService.removeUserEvent(userService.getUser(username).getUser_id(), eventId);
                    if (event.getTrip() == null) {
                        return new ModelAndView("redirect:/myEvents/create/" + eventId);
                    } else
                        return new ModelAndView("redirect:/myEvents/create/" + eventId + "/" + event.getTrip().getTripId());
                }
            }
        }
        request.getSession().setAttribute("error", "Only invited users can delete others. The event owner cannot be removed.");
        model.clear();
        if (event.getTrip() == null) {
            return new ModelAndView("redirect:/myEvents/create/" + eventId);
        } else
            return new ModelAndView("redirect:/myEvents/create/" + eventId + "/" + event.getTrip().getTripId());
    }




    private User getUser() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        return userService.getUser(userDetail.getUsername());
    }


    private boolean canEdit(Event event, HttpServletRequest request) throws Exception {
        User user = getUser();
        User createdBy = eventService.findEventById(event.getEventId()).getCreatedBy();
        if (!createdBy.getUsername().equals(user.getUsername())) {
            return false;
        } else return true;
    }
}
