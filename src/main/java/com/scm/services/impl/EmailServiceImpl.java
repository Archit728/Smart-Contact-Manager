package com.scm.services.impl;

import com.scm.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private JavaMailSender eMailSender;

  // @Value("${spring.mail.properties.domain_name}")
  // private String domainName;

  @Override
  public void sendEmail(String to, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    // message.setFrom(domainName);
    eMailSender.send(message);
  }

  @Override
  public void sendEmailWithAttachment(
    String to,
    String subject,
    String body,
    String pathToAttachment
  ) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'sendEmailWithAttachment'"
    );
  }

  @Override
  public void sendEmailWithHtml(String to, String subject, String body) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException(
      "Unimplemented method 'sendEmailWithHtml'"
    );
  }
}
