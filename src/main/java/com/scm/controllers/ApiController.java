package com.scm.controllers;

import com.scm.entities.Contact;
import com.scm.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//used to show contact details
@RestController   //whenver we make anything restcontroller we mean we are gonna return json data
@RequestMapping("/api")
public class ApiController {

  // get contact
  @Autowired
  private ContactService contactService;

  @GetMapping("/contacts/{contactId}")
  public Contact getContact(@PathVariable String contactId) {
    return contactService.getById(contactId);
  }
}
