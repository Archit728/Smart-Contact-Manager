package com.scm.config;

import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

//since its a custom defined Authentication Failure Handler we have to define methods for all types of exceptions inclduing for wrong credentials entered(BadCredentialsException)
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

  //whenever spring security sees enabled field as false it generates an exception which it handles itself which is of type DisabledException its like a form failure
  //so whenever authentication is failed we have to use this method
  @Override
  public void onAuthenticationFailure(
    HttpServletRequest request,
    HttpServletResponse response,
    AuthenticationException exception
  ) throws IOException, ServletException {
    HttpSession httpSession = request.getSession();

    if (exception instanceof DisabledException) {
      // User is disabled
      httpSession.setAttribute(
        "message",
        Message
          .builder()
          .content("User is disabled, Verification email has been sent.")
          .type(MessageType.red)
          .build()
      );
    } else if (exception instanceof BadCredentialsException) {
      // Wrong password
      httpSession.setAttribute(
        "message",
        Message
          .builder()
          .content("Invalid username or password")
          .type(MessageType.red)
          .build()
      );
    } else {
      // Any other authentication exception
      httpSession.setAttribute(
        "message",
        Message
          .builder()
          .content("Login failed. Please try again.")
          .type(MessageType.red)
          .build()
      );
    }

    response.sendRedirect("/login");
  }
}


//exceptions raised by controllers or MVC based can be handled by global exception handler but the ones genrated by spring security or filters are handled by configurations done like above