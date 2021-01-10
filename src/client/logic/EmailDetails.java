package client.logic;

import java.io.Serializable;

/**
 * EmailDetails class. The class implements Serializable which transmits the
 * information from the client to the server. The class is responsible for
 * holding the information for sending the email to the visitor, the recipient,
 * the subject of the email and the body of the email.
 * 
 * @author Liran Amilov
 */

public class EmailDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	String mailTo;
	String subject;
	String text;

	/**
	 * EmailDetails method. A constructor that contains specific fields.
	 * 
	 * @param mailTo
	 * @param subject
	 * @param text
	 */

	public EmailDetails(String mailTo, String subject, String text) {
		this.mailTo = mailTo;
		this.subject = subject;
		this.text = text;
	}

	/**
	 * getMailTo method.
	 * 
	 * @return mailTo
	 */

	public String getMailTo() {
		return mailTo;
	}

	/**
	 * getSubject method.
	 * 
	 * @return subject
	 */

	public String getSubject() {
		return subject;
	}

	/**
	 * getText method.
	 * 
	 * @return text
	 */

	public String getText() {
		return text;
	}
}
