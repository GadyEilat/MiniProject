package client.logic;

public class EmailDetails {
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
