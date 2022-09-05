package com.hcl.service;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hcl.config.Secret;
import com.hcl.entity.Order;

public class SendEmail {

	// Huge thanks to this page https://mailtrap.io/blog/sending-email-using-java/
	// for guidance on using mailtrap to send an email.
	public static void sendRegistrationEmail(String email, String name) {
		// Put recipient’s address
		String to = email;

		// Put sender’s address
		String from = "no-reply@e-commerce.com";

		Session session = setUpAndGetSession();

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field
			message.setFrom(new InternetAddress(from));

			// Set To: header field
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Welcome to Group 5's E-Commerce App!");

			// Put the content of your message
			message.setText("Hello " + name + ", we hope you enjoy your time on our app.");

			// Send message
			Transport.send(message);

			System.out.println("Sent registration message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sendOrderConfirmation(String email, String username, Order order) {
		String to = email;
		String from = "no-reply@e-commerce.com";

		Session session = setUpAndGetSession();

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Thank you for your order!");

			// Rather than doing this forEach statement, we should look into using templates
			// with something like https://freemarker.apache.org/
			AtomicReference<String> messageContent = new AtomicReference<String>(
					"<h1>Hello " + username + ", thank you for ordering on our E-Commerce website!</h1>"
							+ "\n<h2>Order Details</h2><table><tr><th></th><th></th><th></th><th></th></tr>");
			// AtomicReference allows us to adjust the string in the forEach/lambda scope.
			// We could do a for loop, but this permits use of Java8 features.
			order.getItems().stream().forEach(i -> {
				messageContent.set(messageContent + String.format(
						"<tr><td><img width=\"200\" height=\"200\" src=\"%s\" alt=\"Product Image\" /></td>"
								+ "<td>%s</td><td>$%.2f</td><td>%d</td></tr>",
						i.getProduct().getProductImage(), i.getProduct().getProductName(),
						i.getProduct().getProductPrice(), i.getAmt()));
			});
			messageContent
					.set(messageContent + String.format("</table><h2>Total Price: $%.2f</h2>", order.getTotalPrice()));
			message.setContent(messageContent.get(), "text/html");
			Transport.send(message);
			System.out.println("Sent order confirmation successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private static Session setUpAndGetSession() {
		final String username = Secret.getUsername();
		final String password = Secret.getPassword();

		// Paste host address from the SMTP settings tab in your Mailtrap Inbox
		String host = "smtp.mailtrap.io";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");// it’s optional in Mailtrap
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "2525");// use one of the options in the SMTP settings tab in your Mailtrap Inbox

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		return session;

	}
}