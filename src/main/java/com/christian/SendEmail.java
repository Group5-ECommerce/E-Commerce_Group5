package com.christian;

import java.util.Properties;
import com.christian.Secret;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	
	// Huge thanks to this page https://mailtrap.io/blog/sending-email-using-java/ for guidance on using mailtrap to send an email.
   public static void sendMessage(String email) {
      // Put recipient’s address
      String to = email;

      // Put sender’s address
      String from = "no-reply@e-commerce.com";
      final String username = Secret.username;
      final String password = Secret.password;

      // Paste host address from the SMTP settings tab in your Mailtrap Inbox
      String host = "smtp.mailtrap.io";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");//it’s optional in Mailtrap
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "2525");// use one of the options in the SMTP settings tab in your Mailtrap Inbox

      // Get the Session object.
      Session session = Session.getInstance(props,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(username, password);
    }
         });

      try {
    // Create a default MimeMessage object.
    Message message = new MimeMessage(session);

    // Set From: header field
    message.setFrom(new InternetAddress(from));

    // Set To: header field
    message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));

    // Set Subject: header field
    message.setSubject("Welcome to Group 5's E-Commerce App!");

    // Put the content of your message
    message.setText("Hello new user, we hope you enjoy your time on our app.");

    // Send message
    Transport.send(message);

    System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
         throw new RuntimeException(e);
      }
   }
}