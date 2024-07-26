package com.s.service;

import java.util.Properties;
import org.springframework.stereotype.Service;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	
	
	public boolean sendEmail(String subject, String message, String to) {
		 
		 boolean f=false;
		 
		String  from="ashishtariyal04@gmail.com";
		
		// variable for gmail host-->
		String host = "smtp.gmail.com";

		// get the sytem properties-->

		Properties properties = System.getProperties();
		System.out.println(properties);

		// setting important info to properties object--->

		// host set-->
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// step 1 :to get the session object-->
		Session s = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("ashishtariyal04@gmail.com", "vywm daof qroo rmiw");
			}

		});

		s.setDebug(true);
		// compose the msg[text,multi-media]-->

		MimeMessage m = new MimeMessage(s);

		try {
			// from email-->
			m.setFrom(from);
			// adding recipient to msg-->
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// adding subject to msg-->
			m.setSubject(subject);
			// ading text to msg-->
			m.setText(message);
			m.setContent(message,"text/html");
			// send -->

			// Step 3 :send message using transport class->
			Transport.send(m);
			System.out.println("send successfully!!");
			f=true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;

	}
	
	
	
	
}
