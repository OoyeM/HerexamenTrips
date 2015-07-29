package be.kdg.trips.controllers;

/**
 * Created by Matthias on 25/07/2015.
 */

import be.kdg.trips.model.Trip;
import be.kdg.trips.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    TripService service;

    @Autowired
    MessageSource messageSource;

    /*
     * This method will list all existing employees.
     */
    @RequestMapping(value = { "/tripsList" }, method = RequestMethod.GET)
    public String listEmployees(ModelMap model) {

        List<Trip> trips = service.findAllTrips();
        model.addAttribute("trips", trips);
        return "listView";
    }

    /*
     * This method will provide the medium to add a new employee.
     */
    @RequestMapping(value = { "/new" }, method = RequestMethod.GET)
    public String newEmployee(ModelMap model) {
        Trip trip = new Trip();
        model.addAttribute("trip", trip);
        model.addAttribute("edit", false);
        return "createTrip";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * saving employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/new" }, method = RequestMethod.POST)
    public String saveEmployee(@Valid Trip trip, BindingResult result,
                               ModelMap model) {

        if (result.hasErrors()) {
            return "createTrip";
        }

        /*
         * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation
         * and applying it on field [ssn] of Model class [Employee].
         *
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         *
         */
        service.saveTrip(trip);

        model.addAttribute("success", "Trip " + trip.getTitle() + " registered successfully");
        return "success";
    }


    /*
     * This method will provide the medium to update an existing employee.
     */
    @RequestMapping(value = { "/edit-{tripId}-trip" }, method = RequestMethod.GET)
    public String editEmployee(@PathVariable int tripId, ModelMap model) {
        Trip trip = service.findById(tripId);
        model.addAttribute("trip", trip);
        model.addAttribute("edit", true);
        return "createTrip";
    }

    /*
     * This method will be called on form submission, handling POST request for
     * updating employee in database. It also validates the user input
     */
    @RequestMapping(value = { "/edit-{tripId}-trip" }, method = RequestMethod.POST)
    public String updateEmployee(@Valid Trip trip, BindingResult result,
                                 ModelMap model, @PathVariable int tripId) {

        if (result.hasErrors()) {
            return "createTrip";
        }

        service.updateTrip(trip);

        model.addAttribute("success", "Trip " + trip.getTitle()  + " updated successfully");
        return "success";
    }


    /*
     * This method will delete an employee by it's SSN value.
     */
    @RequestMapping(value = { "/delete-{tripId}-trip" }, method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable int tripId) {
        service.deleteTripById(tripId);
        return "redirect:/list";
    }

    @RequestMapping(value = { "/tripDetails" }, method = RequestMethod.GET)
    public String listEmployees() {

        return "index";
    }
}