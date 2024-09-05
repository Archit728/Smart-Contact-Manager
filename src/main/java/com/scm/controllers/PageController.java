package com.scm.controllers;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

  @Autowired
  private UserService userService;

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
  public String registerPage(Model model) {
    UserForm userForm = new UserForm();
    //we can also send default data
    // userForm.setName("Archit");
    // userForm.setAbout("this is about: I am learning");
    model.addAttribute("userForm", userForm);
    return new String("register");
  }

  //processing signup form
  /* The @ModelAttribute annotation is used to bind the form data (usually submitted via the POST request) to the UserForm object.
    This allows Spring to populate the UserForm object with the form's fields (like username, email, password, etc.). */
  @RequestMapping(value = "/do-register", method = RequestMethod.POST)
  public String processRegister(
   @Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
    HttpSession session
  ) {
    // System.out.println("processing registation");
    // System.out.println(userForm);
    // User user = User
    //   .builder()
    //   .name(userForm.getName())
    //   .email(userForm.getEmail())
    //   .password(userForm.getPassword())
    //   .about(userForm.getAbout())
    //   .phoneNumber(userForm.getPhoneNumber())
    //   .profilePic("http://localhost:8081/images/default_pic.jpg")
    //   .build();
    //builder was unable to put default values

    //validating form
    if(rBindingResult.hasErrors()){
      return "register";
    }
    User user = new User();
    user.setName(userForm.getName());
    user.setEmail(userForm.getEmail());
    user.setPassword(userForm.getPassword());
    user.setAbout(userForm.getAbout());
    user.setPhoneNumber(userForm.getPhoneNumber());
    user.setProfilePic(
      "https://archive.org/download/default_pic/default_pic.jpg"
    );
    userService.saveUser(user);

    //using session to handle message to be displayed
    Message message = Message
      .builder()
      .content("Registration Successful!")
      .type(MessageType.green)
      .build();
    session.setAttribute("message", message);
    return "redirect:/register"; //this will redirect to this route(/register)
  }
}
