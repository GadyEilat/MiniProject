package server.Controller;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
	// Recipient's email
	String mailTo;
	String subject;
	String text;
	final String user = "G5GoNature@gmail.com";
	final String password = "G5@GoNature!s";
	final String port = "587";
	String host = "smtp.gmail.com";

	public SendEmail(String mailTo,String subject,String text) {
		this.mailTo=mailTo;
		this.subject=subject;
		this.text=text;
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
			message.setSubject(subject);
			message.setText(text);

			// send the message
			Transport.send(message);

			ServerController.instance.displayMsg("message sent successfully to : " + mailTo);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}


}
