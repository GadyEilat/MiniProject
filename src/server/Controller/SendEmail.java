package server.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

import sun.misc.IOUtils;

public class SendEmail {
	// Recipient's email
	String mailTo;
	String subject;
	String text;
	final String user = "G5GoNature@gmail.com";
	final String password = "G5@GoNature!s";
	final String port = "587";
	String host = "smtp.gmail.com";

	public SendEmail(String mailTo, String subject, String text) {
		this.mailTo = mailTo;
		this.subject = subject;
		this.text = text;
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
//			message.setText(text);
//			message.setContent("<img src=\"cid:/LOGONATURE.png\"/>","text/html");

//			BodyPart messageBodyPart = new MimeBodyPart();
//			messageBodyPart.setText(text);
//			Multipart multipart = new MimeMultipart();
//			multipart.addBodyPart(messageBodyPart);
//			
//	         messageBodyPart = new MimeBodyPart();
//	         String filename = "/LOGONATURE.png";
//	         DataSource source = new FileDataSource(filename);
//	         messageBodyPart.setDataHandler(new DataHandler(source));
//	         messageBodyPart.setFileName(filename);
//	         multipart.addBodyPart(messageBodyPart);
//
//	         // Send the complete message parts
//	         message.setContent(multipart );
	 
			BodyPart messageBodyPart = new MimeBodyPart();
//	         messageBodyPart.setText("This is message body");

			String htmlText = "<img src=\"cid:image\">"; ////// if we want head <H1>Hello</H1>
			messageBodyPart.setContent("<p>"+text+"</p>"+htmlText, "text/html");
			// add it
			MimeMultipart multipart = new MimeMultipart("related");

			multipart.addBodyPart(messageBodyPart);

			// second part (the image)
			messageBodyPart = new MimeBodyPart();

			java.io.InputStream inputStream = this.getClass().getResourceAsStream("/LOGOFORMAIL.png");

			try {
				ByteArrayDataSource ds = new ByteArrayDataSource(inputStream, "image/jpg");
				messageBodyPart.setDataHandler(new DataHandler(ds));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(inputStream);

			messageBodyPart.setHeader("Content-ID", "<image>");

			messageBodyPart.setDisposition(MimeBodyPart.INLINE);

			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			// send the message
			Transport.send(message);

			ServerController.instance.displayMsg("message sent successfully to : " + mailTo);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
