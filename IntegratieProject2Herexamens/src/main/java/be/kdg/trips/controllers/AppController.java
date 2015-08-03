package be.kdg.trips.controllers;

/**
* Created by Matthias on 25/07/2015.
*/

import be.kdg.trips.model.Trip;
import be.kdg.trips.model.TripImage;
import be.kdg.trips.model.TripLocation;
import be.kdg.trips.service.TripImageService;
import be.kdg.trips.service.TripLocationService;
import be.kdg.trips.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
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
import java.util.List;


@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    TripService tripService;

    @Autowired
    TripLocationService tripLocationService;

    @Autowired
    TripImageService tripImageService;

    @Autowired
    MessageSource messageSource;

    @PreAuthorize("permitAll")
    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView index(ModelMap model) {

        List<Trip> trips = tripService.findAllTrips();
        model.addAttribute("trips", trips);
        return new ModelAndView("index");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = { "/tripsList" }, method = RequestMethod.GET)
    public ModelAndView tripsList(ModelMap model) {

        List<Trip> trips = tripService.findAllTrips();
        model.addAttribute("trips", trips);
        return new ModelAndView("listView");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = { "/new" }, method = RequestMethod.GET)
    public ModelAndView newTrip(ModelMap model) {
        Trip trip = new Trip();
        model.addAttribute("trip", trip);
        model.addAttribute("edit", false);
        return new ModelAndView("createTrip");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = { "/new" }, method = RequestMethod.POST)
    public ModelAndView saveTrip(@Valid Trip trip, BindingResult result,
                               ModelMap model) {
        if (result.hasErrors()) {
            return new ModelAndView("createTrip");
        }
        tripService.saveTrip(trip);
        model.addAttribute("success", "Trip " + trip.getTitle() + " registered successfully");
        return new ModelAndView("success");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = { "/editTrip/{tripId}" }, method = RequestMethod.GET)
    public ModelAndView editTrip(@PathVariable int tripId, ModelMap model) {
        Trip trip = tripService.findTripById(tripId);
        List<TripLocation> tripLocations = tripLocationService.findAllTripLocations(tripId);
        model.addAttribute("tripLocations",tripLocations);
        model.addAttribute("trip", trip);
        model.addAttribute("edit", true);
        return new ModelAndView("tripEdit");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = { "/editTrip/{tripId}" }, method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView updateTrip(@Valid Trip trip, BindingResult result,
                                 ModelMap model, @PathVariable int tripId) {

        if (result.hasErrors()) {
            return new ModelAndView("tripEdit");
        }

        tripService.updateTrip(trip);
        return new ModelAndView("redirect:/tripsList");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = { "/editTrip/{tripId}/createLocation/{lng}/{lat}/" }, method = RequestMethod.GET)
    public ModelAndView createLocation(@PathVariable int tripId,@PathVariable double lng,@PathVariable double lat,ModelMap model) {
        TripLocation tripLocation = new TripLocation();
        tripLocation.setLat(lat);
        tripLocation.setLng(lng);
        Trip trip = tripService.findTripById(tripId);
        tripLocation.setTrip(trip);
        tripLocationService.saveTripLocation(tripLocation);
        model.addAttribute("tripLocation",tripLocation);
        return new ModelAndView("redirect:/createLocation/"+tripLocation.getLocationId());
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = { "/createLocation/{tripLocationId}" }, method = RequestMethod.GET)
    public ModelAndView editLocation(@PathVariable int tripLocationId,ModelMap model) {
        TripLocation tripLocation = tripLocationService.findTripLocationById(tripLocationId);
        model.addAttribute("tripLocation",tripLocation);
        List<TripImage> images = tripImageService.getAllLocationImages(tripLocationId);
        model.addAttribute("images",images);
        return new ModelAndView("createLocation");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = { "/createLocation/{tripLocationId}" }, method = RequestMethod.POST)
    public ModelAndView updateLocation(@Valid TripLocation tripLocation, BindingResult result, ModelMap model, @PathVariable int tripLocationId) {
        tripLocation.setLocationId(tripLocationId);
        tripLocationService.updateTripLocation(tripLocation);
        model.addAttribute(tripLocation);
        return new ModelAndView("createLocation");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = { "/delete-{tripId}-trip" }, method = RequestMethod.GET)
    public ModelAndView deleteTrip(@PathVariable int tripId) {
        tripService.deleteTripById(tripId);
        return new ModelAndView("redirect:/tripsList");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = { "/trip/{tripId}" }, method = RequestMethod.GET)
    public ModelAndView getTrip(@PathVariable int tripId,ModelMap model) {
        Trip trip = tripService.findTripById(tripId);
        model.addAttribute("trip", trip);
        return new ModelAndView("tripDetails");
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied() {

        ModelAndView model = new ModelAndView();

        // check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            System.out.println(userDetail);

            model.addObject("username", userDetail.getUsername());

        }

        model.setViewName("403");
        return model;

    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}

    //	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

}