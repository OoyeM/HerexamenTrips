package be.kdg.trips.controllers;

/**
 * Created by Matthias on 25/07/2015.
 */

import be.kdg.trips.model.Trip;
import be.kdg.trips.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/")
@Scope("session")
public class AppController {

    @Autowired
    TripService tripService;

    @PreAuthorize("permitAll")
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                              @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                              @RequestParam(value = "search", required = false, defaultValue = "") String keyWord
            , ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {

    /* The user is logged in :) */
            return new ModelAndView("redirect:/trips");
        }

        List<Trip> trips = tripService.findAllTrips(offset, limit, keyWord);
        model.addAttribute("trips", trips);
        model.addAttribute("count", tripService.count(offset, limit, keyWord));
        model.addAttribute("offset", offset);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("limit", limit);
        return new ModelAndView("index");
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




}