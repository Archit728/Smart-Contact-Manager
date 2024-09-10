package com.scm.controllers;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.UUID;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * pagecontroller for showing all pages
 * rootcontroller for inserting loggedInUser in every request
 * Usercontroller for user related operations
 * contactController for contact related operations and only accessible to the person who is logged in
 */
@Controller
@RequestMapping("/user/contacts")
public class ContactController {

  @Autowired
  private ContactService contactService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private UserService userService;

  private Logger logger = org.slf4j.LoggerFactory.getLogger(
    ContactController.class
  );

  @RequestMapping("/add")
  //add contact page: handler
  public String addContactView(Model model) {
    ContactForm contactForm = new ContactForm();

    contactForm.setFavourite(true);
    model.addAttribute("contactForm", contactForm);
    return "user/add_contact";
  }

  //add contact form handler
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String saveContact(
    @Valid @ModelAttribute ContactForm contactForm,
    BindingResult result,
    Authentication authentication,
    HttpSession session
  ) {
    // process the form data
    // 1 validate form
    if (result.hasErrors()) {
      result.getAllErrors().forEach(error -> logger.info(error.toString()));

      session.setAttribute(
        "message",
        Message
          .builder()
          .content("Please correct the following errors")
          .type(MessageType.red)
          .build()
      );
      return "user/add_contact";
    }

    String username = Helper.getEmailOfLoggedInUser(authentication);

    // form ---> contact
    User user = userService.getUserByEmail(username);

    // to upload
    Contact contact = new Contact();
    contact.setName(contactForm.getName());
    contact.setFavourite(contactForm.isFavourite());
    contact.setEmail(contactForm.getEmail());
    contact.setPhoneNumber(contactForm.getPhoneNumber());
    contact.setAddress(contactForm.getAddress());
    contact.setDescription(contactForm.getDescription());
    contact.setUser(user);
    contact.setLinkedInLink(contactForm.getLinkedInLink());
    contact.setWebsiteLink(contactForm.getWebsiteLink());

    // 2 process the contact picture
    // image process
    if (
      contactForm.getContactImage() != null &&
      !contactForm.getContactImage().isEmpty()
    ) {
      String filename = UUID.randomUUID().toString();
      String fileURL = imageService.uploadImage(
        contactForm.getContactImage(),
        filename
      );
      contact.setPicture(fileURL);
      contact.setCloudinaryImagePublicId(filename);
    }
    contactService.save(contact);
    System.out.println(contactForm);

    // 3 set the contact picture url
    // 4 set message to be displayed on the view
    session.setAttribute(
      "message",
      Message
        .builder()
        .content("You have successfully added a new contact")
        .type(MessageType.green)
        .build()
    );

    contactService.save(contact);
    return "redirect:/user/contacts/add";
  }
}
