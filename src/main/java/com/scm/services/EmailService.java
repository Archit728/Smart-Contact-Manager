package com.scm.services;

//java mail sender dependency is used to send emails
public interface EmailService {
  void sendEmail(String to, String subject, String body);
  void sendEmailWithAttachment(
    String to,
    String subject,
    String body,
    String pathToAttachment
  );
  void sendEmailWithHtml(String to, String subject, String body);
}
