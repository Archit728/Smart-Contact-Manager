package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

  @RequestMapping("/home")
  public String home(Model model) {
    System.out.println("Home Page Handler");
    //sending data to view
    model.addAttribute("name", "Testing page");
    model.addAttribute("channel", "Youtube page");
    model.addAttribute("githubRepo", "https://github.com/");
    return "home";
  }

  // about route
  @RequestMapping("/about")
  public String aboutPage(Model model) {
    model.addAttribute("isLogin", true);
    System.out.println("About page loading");
    return "about";
  }

  // services
  @RequestMapping("/services")
  public String servicesPage() {
    System.out.println("services page loading");
    return "services";
  }

  //contact page
  @GetMapping("/contact")
  public String contactPage() {
    return new String("contact");
  }

  @GetMapping("/login")
  public String loginPage() {
    return new String("login");
  }

  @GetMapping("/register")
  public String registerPage() {
    return new String("register");
  }
}
