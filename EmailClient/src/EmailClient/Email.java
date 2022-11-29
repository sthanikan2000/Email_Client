package EmailClient;

import java.io.Serializable;

public class Email implements Serializable {	
	
	private static final long serialVersionUID = 2168972780981051449L;	
	private String to = "";
	private String subject = "";
	private String text = "";
	private String time = "";
	
	public Email(String to, String subject, String text, String time) {
		this.to = to;
		this.subject = subject;
		this.text = text;
		this.time = time;
	}
	public String getTo() {
		return to;
	}
	public String getSubject() {
		return subject;
	}
	public String getText() {
		return text;
	}
	public String getTime() {
		return time;
	}
	public void printMail() {
		System.out.println("Subject: " + this.getSubject() 
		+ "\nRecipient_TO: " + this.getTo()
		+ "\nTime Email Sent: " + this.getTime() 
		+ "\n");
	}
	
}
