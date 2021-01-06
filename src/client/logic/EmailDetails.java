package client.logic;

import java.io.Serializable;

public class EmailDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String mailTo;
	String subject;
	String text;
	
	public EmailDetails(String mailTo, String subject, String text) {
		this.mailTo = mailTo;
		this.subject = subject;
		this.text = text;
	}

	public String getMailTo() {
		return mailTo;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}
}
