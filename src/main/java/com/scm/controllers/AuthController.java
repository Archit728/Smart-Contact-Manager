package com.scm.controllers;

import com.scm.entities.User;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserRepo userRepo;

  private User getUserByToken(String emailToken) {
    // get user by email token
    return userRepo.findByEmailVerificationToken(emailToken).orElse(null);
  }

  //verify mail
  @GetMapping("/verify-email")
  public String verifyEmail(
    @RequestParam("token") String token,
    HttpSession session
  ) {
    User user = getUserByToken(token);
    if (user != null && user.getEmailVerificationToken().equals(token)) {
      //user is fetched
      //token is correct
      user.setEmailVerified(true);
      user.setEnabled(true);
      userRepo.save(user);
      session.setAttribute(
        "message",
        Message
          .builder()
          .type(MessageType.green)
          .content("Your email is verified successfully! You can now login.")
          .build()
      );
      return "success_page";
    }

    session.setAttribute(
      "message",
      Message
        .builder()
        .type(MessageType.red)
        .content("Email not verified! Token is not associated with user.")
        .build()
    );
    return "error_page";
  }
}
