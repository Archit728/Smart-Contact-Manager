package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;

/*
 * to handle exceptions and add global data across multiple controllers. 
 * It acts as a centralized mechanism for handling concerns like error handling, pre-processing, or adding common model attributes in a Spring MVC application.
 * Key Annotations Used with @ControllerAdvice:
 *  @ExceptionHandler: Handles exceptions thrown by controller methods.
 *  @ModelAttribute: Adds attributes to the model that are visible to all views.
 *  @InitBinder: Used to customize request parameter binding (e.g., formatting or validation).
 * basically all the methods in this class will be available to all the controllers and gets executed for all the requests
 */
@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    //function to add logged in user information in all the routes
    @ModelAttribute
    // When used on a method, it is invoked before every request handling method in the controller. 
    // It can add common objects to the model that will be available to every view.
    // we will add null in ncase the user is not logged in and will make it available for all types of requests instead of just /user
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        logger.info("Adding logged in user information to the model");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username);
        // fetching 
        User user = userService.getUserByEmail(username);
        System.out.println(user);
        logger.info(user.getName());
        logger.info(user.getEmail());
        model.addAttribute("loggedInUser", user);

    }
}
