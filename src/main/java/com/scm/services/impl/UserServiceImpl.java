package com.scm.services.impl;

import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.EmailService;
import com.scm.services.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepo userRepo; //we also could have used constructor based dependency injection instead of field one

  @Autowired
  private PasswordEncoder passwordEncoder;

  private Logger logger = LoggerFactory.getLogger(this.getClass()); //to log

  @Autowired
  private EmailService emailService;

  @Override
  public User saveUser(User user) {
    //before saving we need to generate the id dynamically
    String userId = UUID.randomUUID().toString(); //generates a long string
    user.setUserId(userId);
    //setting the user password
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    //setting the user role
    user.setRoleList(List.of(AppConstants.ROLE_USER));
    logger.info(user.getProvider().toString());

    //sending email verification link upon registration
    String emailtoken = UUID.randomUUID().toString();
    user.setEmailVerificationToken(emailtoken);
    User savedUser = userRepo.save(user);
    String emailLink = Helper.getLinkForEmailVerificatiton(emailtoken);
    emailService.sendEmail(
      savedUser.getEmail(),
      "Verify Account: Email Verification for Smart Contact Manager",
      emailLink
    );
    return savedUser;
  }

  @Override
  public void deleteUser(String id) {
    User user2 = userRepo
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    userRepo.delete(user2);
  }

  @Override
  public List<User> getAllUsers() {
    return userRepo.findAll();
  }

  @Override
  public User getUserByEmail(String email) {
    return userRepo.findByEmail(email).orElse(null);
  }

  @Override
  public Optional<User> getUserById(String id) {
    return userRepo.findById(id);
  }

  @Override
  public boolean isUserExist(String userId) {
    User user2 = userRepo.findById(userId).orElse(null);
    return user2 != null ? true : false;
  }

  @Override
  public boolean isUserExistByEmail(String email) {
    User user = userRepo.findByEmail(email).orElse(null);
    return user != null ? true : false;
  }

  @Override
  public Optional<User> updateUser(User user) {
    //fetching the user from database
    User user2 = userRepo
      .findById(user.getUserId())
      .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    //updating user2 from user
    user2.setName(user.getName());
    user2.setEmail(user.getEmail());
    user2.setPassword(user.getPassword());
    user2.setAbout(user.getAbout());
    user2.setPhoneNumber(user.getPhoneNumber());
    user2.setProfilePic(user.getProfilePic());
    user2.setEnabled(user.isEnabled());
    user2.setEmailVerified(user.isEmailVerified());
    user2.setPhoneVerified(user.isPhoneVerified());
    user2.setProvider(user.getProvider());
    user2.setProviderUserId(user.getProviderUserId());

    //save the user in database after updating
    User save = userRepo.save(user2);
    return Optional.ofNullable(save);
  }
}
