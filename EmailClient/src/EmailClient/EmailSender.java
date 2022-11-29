package EmailClient;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

public class EmailSender {
	// -------------------------------------------------------------------------------------------------
		private static final String username = "purposetmailtesting@gmail.com";
		private static final String password = "lccspyiwyallazts";
		//Use app password for password and turn on two-step authentication.
		
		private static Properties prop = new Properties();
		private static Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		// -------------------------------------------------------------------------------------------------
		private static void setProperties() {
			prop.put("mail.smtp.host", "smtp.gmail.com");
			prop.put("mail.smtp.port", "587");
			prop.put("mail.smtp.auth", "true");
			
			prop.put("mail.smtp.starttls.enable", "true");//TLS
			// Transport Layer Security (TLS) is a security protocol that encrypts email for privacy.
			//prevents unauthorized access of your email when it's in transit over Internet connections.
			//By default, Gmail always tries to use a secure TLS connection when sending email.
		}

		// -------------------------------------------------------------------------------------------------
		public static boolean sendMail(String to, String subject, String text, String cc, String bcc) {
			
			setProperties();
			// setting >>EmailSender.prop<< before sending mail
			
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
				message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
				message.setSubject(subject);
				message.setText(text);

				Transport.send(message);
				
			} catch (MessagingException e) {
				System.out.println("Exception "+e+"occurred. Redirecting to main menu.....");
				return false;
			}
			System.out.println("Finished sending mail.");
			return true;
			//Return true only when email sending take place in email server without Exception.
		}
		// -------------------------------------------------------------------------------------------------
		public static Email sendCustomMail_TO_only(String mail) {
			
			String[] arr = mail.strip().split(",");
			if (EmailValidator.isValidEmail(arr[0].strip())) {
				System.out.println("Start sending mail.....\nWAIT FOR THE PROGRESS.....");
				if (sendMail(arr[0].strip(),arr[1].strip(),arr[2].strip().toString(),"",""))
					return new Email(arr[0].strip(),arr[1].strip(),arr[2].strip().toString(),DateValidator.getTime());
				else
					return null;
			}
			else {
				System.out.println("Invalid Email Address.Can't send mail.");
				return null;
			}
		}

		// -------------------------------------------------------------------------------------------------
		public static Email sendBirthdayWishMail
						(String birthdayGreeting, String recipientMail, String recipientName) {
			//Design the content(text) of mail
			String text="Dear "+recipientName+",\n\n"+birthdayGreeting;
			
			System.out.println("Start sending Birthay wish mail to: " + recipientName + "....");		
			if (sendMail(recipientMail, "Birthday Wish",text , "", ""))
				// When mail sending action take place in server			
				return new Email(recipientMail, "Birthday Wish", text,DateValidator.getTime());
			else
				// Return null when email sending failed
				return null;
		}
		
		// -------------------------------------------------------------------------------------------------
		public static String updateRecipient(String type) {
			//The following method can be used to update Recipient_Types: TO,CC & BCC
			String recipient="";
			Scanner input = new Scanner(System.in);
			System.out.println("IF you want to add Recipient_Type_"
								+type
								+": Input 1, ELSE: Input any other key");
			try {
				int condition1 = input.nextInt();
				while (condition1 == 1) {
					System.out.println("Enter the email address of Recipient_Type_"+type+": ");
					String tem = (new Scanner(System.in)).nextLine();
					
					//we have to check for entered email address can be considered as a mail address
					if (EmailValidator.isValidEmail(tem)) recipient += (tem + ",");
					else System.out.println("Invalid email address");
					
					//we can add multiple recipients to same type
					System.out.println("IF you want to add another Recipient to Type_"+type
										+": Input 1,ELSE: Input any other key ");
					int condition2 = input.nextInt();
					if (condition2 != 1) break;
				}
			}catch (InputMismatchException e) {
				return recipient;
			}
			return recipient;
		}
		
		// -------------------------------------------------------------------------------------------------	
		public static Email sendCustomMail() {
			String to, cc = "", bcc = "", subject = "", text = "";

			to=updateRecipient("TO");
			//cc=updateRecipient("CC");
			//bcc=updateRecipient("BCC");		
			
			//update subject of the mail
			System.out.println("Enter the subject of the mail: (If you want to skip press ENTER)");
			subject += (new Scanner(System.in)).nextLine();

			//update text content of the mail
			System.out.println("Enter the text content of the mail: (If you want to skip press ENTER)");
			text += (new Scanner(System.in)).nextLine();

			if ((to + cc + bcc).equals("")) {
				System.out.println("Can't send mail: Empty Recipients");
				return null;
			} 
			else {
				//Remove extra coma (',')  added to end of TO,CC & BCC
				if (to.length() > 0) 	to = to.substring(0, to.length() - 1);
				if (cc.length() > 0)	cc = cc.substring(0, cc.length() - 1);
				if (bcc.length() > 0)	bcc = bcc.substring(0, bcc.length() - 1);
				
				System.out.println("Start sending mail.....\nWAIT FOR THE PROGRESS.....");
				if (sendMail(to, subject, text, cc, bcc))
					// When mail sending action take place in server
					return new Email(to, subject, text, DateValidator.getTime());
				else 
					// Return null when email sending failed
					return null;
					
			}
		}
		
}
