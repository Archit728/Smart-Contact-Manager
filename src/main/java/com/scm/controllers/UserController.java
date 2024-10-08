package com.scm.controllers;

import com.scm.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//handling and securing all the user related routes
@Controller
@RequestMapping("/user")
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  // user dashboard page
  // @RequestMapping(value = "/dashboard")
  // public String userDashboard() {
  //   System.out.println("User dashboard");
  //   return "user/dashboard";
  // }

  // user profile page
  @RequestMapping(value = "/profile")
  public String userProfile(Model Model, Authentication authentication) {
    // String username = Helper.getEmailOfLoggedInUser(authentication);
    // logger.info("User logged in {}", username);
    // //fetching data from database using the email which acts as our username
    // User user = userService.getUserByEmail(username);
    // logger.info(user.getName());
    // logger.info(user.getEmail());
    // Model.addAttribute("loggedInUser", user);
    return "user/profile";
  }
  // user add contacts page

  // user view contacts

  // user edit contact

  // user delete contact

}
