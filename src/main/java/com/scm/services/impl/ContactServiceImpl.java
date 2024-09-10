package com.scm.services.impl;

import com.scm.entities.Contact;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

  @Autowired
  private ContactRepo contactRepo;

  @Override
  public Contact save(Contact contact) {
    String contactId = UUID.randomUUID().toString();
    contact.setId(contactId);
    return contactRepo.save(contact);
  }

  @Override
  public Contact update(Contact contact) {
    var contactOld = contactRepo
      .findById(contact.getId())
      .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
    contactOld.setName(contact.getName());
    contactOld.setEmail(contact.getEmail());
    contactOld.setPhoneNumber(contact.getPhoneNumber());
    contactOld.setAddress(contact.getAddress());
    contactOld.setDescription(contact.getDescription());
    contactOld.setPicture(contact.getPicture());
    contactOld.setFavourite(contact.isFavourite());
    contactOld.setWebsiteLink(contact.getWebsiteLink());
    contactOld.setLinkedInLink(contact.getLinkedInLink());

    return contactRepo.save(contactOld);
  }

  @Override
  public List<Contact> getAll() {
    return contactRepo.findAll();
  }

  @Override
  public Contact getById(String id) {
    return contactRepo
      .findById(id)
      .orElseThrow(() ->
        new ResourceNotFoundException("Contact not found with given id " + id)
      );
  }

  @Override
  public void delete(String id) {
    var contact = contactRepo
      .findById(id)
      .orElseThrow(() ->
        new ResourceNotFoundException("Contact not found with given id " + id)
      );
    contactRepo.delete(contact);
  }

  @Override
  public List<Contact> getByUserId(String userId) {
    return contactRepo.findByUserId(userId);
  }
}
