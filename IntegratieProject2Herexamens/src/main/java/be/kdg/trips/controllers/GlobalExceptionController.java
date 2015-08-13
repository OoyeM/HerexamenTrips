package be.kdg.trips.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
* Created by Matthias on 13/08/2015.
*/
    @ControllerAdvice
public class GlobalExceptionController {
    private static final Logger logger = Logger.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptionsExceptAccesDenied(HttpServletRequest req, Exception exception) throws Exception {

        if(exception instanceof AccessDeniedException){
            throw exception;
        }
        logger.error("Request: " + req.getRequestURL() + " raised " + exception,exception);
        ModelAndView model = new ModelAndView("error");
        return model;
    }


}