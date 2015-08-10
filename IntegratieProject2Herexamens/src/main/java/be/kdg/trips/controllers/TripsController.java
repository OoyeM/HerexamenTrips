package be.kdg.trips.controllers;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * Created by Matthias on 9/08/2015.
 */
@Controller
@RequestMapping("/")
@Scope("session")
public class TripsController {
    @Autowired
    TripService tripService;

    @Autowired
    TripLocationService tripLocationService;

    @Autowired
    TripImageService tripImageService;

    @Autowired
    TripLabelService tripLabelService;

    @Autowired
    UserService userService;

    @Autowired
    EventService eventService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/trips"}, method = RequestMethod.GET)
    public ModelAndView tripsList(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                  @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                  @RequestParam(value = "search", required = false, defaultValue = "") String keyWord
            , ModelMap model, HttpServletRequest request) {
        String error = (String) request.getSession().getAttribute("error");
        model.addAttribute("error", error);
        request.getSession().removeAttribute("error");
        List<Trip> trips = tripService.findAllTrips(offset, limit, keyWord);
        model.addAttribute("trips", trips);
        model.addAttribute("count", tripService.count(offset, limit, keyWord));
        model.addAttribute("offset", offset);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("limit", limit);

        return new ModelAndView("trips");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/myTrips"}, method = RequestMethod.GET)
    public ModelAndView myTripsList(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                    @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                    @RequestParam(value = "search", required = false, defaultValue = "") String keyWord
            , ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.getUser(userDetail.getUsername());
        List<Trip> trips = tripService.findAllTripsByUsername(offset, limit, keyWord, user.getUser_id());
        model.addAttribute("trips", trips);
        model.addAttribute("count", tripService.count(offset, limit, keyWord, user.getUser_id()));
        model.addAttribute("offset", offset);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("limit", limit);
        return new ModelAndView("myTrips");
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/new"}, method = RequestMethod.GET)
    public ModelAndView newTrip(ModelMap model) {
        Trip trip = new Trip();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userService.getUser(userDetail.getUsername());
        trip.setCreatedBy(user);
        tripService.updateTrip(trip);
        tripService.saveTrip(trip);
        model.addAttribute("trip", trip);
        return new ModelAndView("redirect:/editTrip/" + trip.getTripId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/editTrip/{tripId}"}, method = RequestMethod.GET)
    public ModelAndView editTrip(@PathVariable int tripId, ModelMap model, HttpServletRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Trip trip = tripService.findTripById(tripId);
        String createdBy = trip.getCreatedBy().getUsername();
        if (!createdBy.equals(userDetail.getUsername())) {
            request.getSession().setAttribute("error", "Trips can only be edited by their owners: " + createdBy);
            model.clear();
            return new ModelAndView("redirect:/trips", model);
        }
        List<TripLocation> tripLocations = tripLocationService.findAllTripLocations(tripId);
        model.addAttribute("tripLocations", tripLocations);
        model.addAttribute("trip", trip);
        return new ModelAndView("tripEdit");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/editTrip/{tripId}"}, method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView updateTrip(@Valid Trip trip, BindingResult result,
                            ModelMap model, @PathVariable int tripId) {

        if (result.hasErrors()) {
            return new ModelAndView("tripEdit");
        }

        tripService.updateTrip(trip);
        return new ModelAndView("redirect:/editTrip/" + tripId);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/editTrip/{tripId}/editLabels"}, method = RequestMethod.GET)
    public
    @ResponseBody
    ModelAndView editLabels(ModelMap model, @PathVariable int tripId, HttpServletRequest request) {

        if (!correctUser(tripId)) {
            request.getSession().setAttribute("error", "Tripslabel can only be added by the trip owner.");
            model.clear();
            return new ModelAndView("redirect:/trips", model);
        }
        List<TripLabel> labels = tripLabelService.findAllTripLabels(tripId);
        TripLabel label = new TripLabel();
        model.addAttribute("label", label);
        model.addAttribute("labels",labels);
        return new ModelAndView("editLabel");
    }



    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/editTrip/{tripId}/editLabels"}, method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView saveLabel(@Valid TripLabel label, ModelMap model, @PathVariable int tripId, HttpServletRequest request) {
        if (!correctUser(tripId)) {
            request.getSession().setAttribute("error", "Tripslabel can only be added by the trip owner.");
            model.clear();
            return new ModelAndView("redirect:/trips", model);
        }
        label.setTrip(tripService.findTripById(tripId));
        tripLabelService.saveTrip(label);
        return new ModelAndView("redirect:/editTrip/"+tripId+"/editLabels");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/editTrip/{tripId}/deleteLabel/{labelId}"}, method = RequestMethod.GET)
    public
    @ResponseBody
    ModelAndView deleteLabel(@Valid TripLabel label, ModelMap model, @PathVariable int tripId, @PathVariable int labelId, HttpServletRequest request) {
        if (!correctUser(tripId)) {
            request.getSession().setAttribute("error", "Tripslabel can only be deleted by the trip owner.");
            model.clear();
            return new ModelAndView("redirect:/trips", model);
        }
        tripLabelService.deleteLabel(labelId);
        return new ModelAndView("redirect:/editTrip/"+tripId+"/editLabels");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/editTrip/{tripId}/createLocation/{lng}/{lat}/"}, method = RequestMethod.GET)
    public ModelAndView createLocation(@PathVariable int tripId, @PathVariable double lng, @PathVariable double lat, ModelMap model) {
        TripLocation tripLocation = new TripLocation();
        tripLocation.setLat(lat);
        tripLocation.setLng(lng);
        Trip trip = tripService.findTripById(tripId);
        tripLocation.setTrip(trip);
        tripLocationService.saveTripLocation(tripLocation);
        model.addAttribute("tripLocation", tripLocation);
        return new ModelAndView("redirect:/createLocation/" + tripLocation.getLocationId());
    }

    @RequestMapping(value = {"/{tripId}/locations/{tripLocationId}"}, method = RequestMethod.GET)
    public ModelAndView LocationDetails(@PathVariable int tripId, @PathVariable int tripLocationId, ModelMap model) {
        TripLocation tripLocation = tripLocationService.findTripLocationById(tripLocationId);
        model.addAttribute("tripLocation", tripLocation);
        model.addAttribute("tripId", tripId);
        return new ModelAndView("locationDetails");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/createLocation/{tripLocationId}"}, method = RequestMethod.GET)
    public ModelAndView editLocation(@PathVariable int tripLocationId, ModelMap model) {
        TripLocation tripLocation = tripLocationService.findTripLocationById(tripLocationId);
        model.addAttribute("tripLocation", tripLocation);
        List<TripImage> images = tripImageService.getAllLocationImages(tripLocationId);
        model.addAttribute("images", images);
        return new ModelAndView("createLocation");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/createLocation/{tripLocationId}"}, method = RequestMethod.POST)
    public ModelAndView updateLocation(@Valid TripLocation tripLocation, BindingResult result, ModelMap model, @PathVariable int tripLocationId) {
        tripLocation.setLocationId(tripLocationId);
        tripLocationService.updateTripLocation(tripLocation);
        model.addAttribute(tripLocation);
        return new ModelAndView("createLocation");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = {"/delete-{tripId}-trip"}, method = RequestMethod.GET)
    public ModelAndView deleteTrip(@PathVariable int tripId) {
        tripService.deleteTripById(tripId);
        return new ModelAndView("redirect:/tripsList");
    }

    @RequestMapping(value = {"/trip/{tripId}"}, method = RequestMethod.GET)
    public ModelAndView getTrip(@PathVariable int tripId, ModelMap model) {

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
        model.addAttribute("labels", labels);
        model.addAttribute("tripLocations", tripLocations);
        model.addAttribute("trip", trip);


        return new ModelAndView("tripDetails_NoUser");

    }
    private boolean correctUser(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Trip trip = tripService.findTripById(id);
        String createdBy = trip.getCreatedBy().getUsername();
        if (!createdBy.equals(userDetail.getUsername())) {
            return false;
        }
        return true;
    }
}
