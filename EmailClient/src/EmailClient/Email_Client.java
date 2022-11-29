package EmailClient;

import java.io.*;
import java.util.*;


public class Email_Client {
	// ---------------------------------------------------------------------------------------------------
	public static void 
		startEmailClient(ArrayList<Recipient> recipients, ArrayList<IBirthday> birthdayToday)
															throws IOException,ClassNotFoundException{
		// Get all the Recipients in "clientList.txt" to our running email client as Objects
		FilesHandler.getRecipientsFromFile(recipients, birthdayToday);

		// send Birthday wish to recipients having birthday
		if (birthdayToday.isEmpty())
			System.out.println("\nToday there is no Birthdays.");
		else {
			
			System.out.println("\nStart sending BirthDay wishes....");
			for (IBirthday r : birthdayToday) {
				String greeting = r.getBirthdayGreeting();
				Recipient recipient = (Recipient) r;
				Email email = 
					EmailSender.sendBirthdayWishMail(greeting, recipient.getEmail(), recipient.getName());
				// code to add the entry of mail to hashmap
				FilesHandler.updateEmailsSentEntry(email);
			}
			System.out.println("Finished sending BirthDay wishes.\n");
		}
	}
	// -------------------------------------------------------------------------------------------------
	public static void addNewRecipient(ArrayList<Recipient> recipients) throws IOException {
		System.out.println("\nEnter the new recipient details in one of the following formats,"
				+ "\nOfficial:<name>,<email>,<designation>" 
				+ "\nOffice_friend:<name>,<email>,<designation>,<birthday>"
				+ "\nPersonal:<name>,<nick-name>,<email>,<birthday>"
				+ "\n-----DON'TLEAVE ANY WHITE SPACES OR UNWANTED CHARACTERS WITHIN THE ENTRY-----");

		Scanner userInput = new Scanner(System.in);
		String[] arr1 = userInput.nextLine().split(":");

		if (arr1.length == 2) {
			String[] arr2 = arr1[1].split(",");
			if (arr1[0].equalsIgnoreCase("Official") && arr2.length == 3) {
				if (EmailValidator.isValidEmail(arr2[1].strip())) {
					// code to add a new recipient to ArrayList of recipients
					Official tem=new Official(arr2[0].strip(), arr2[1].strip(), arr2[2].strip());
					recipients.add(tem);

					// store details in clientList.txt file
					FilesHandler.addRecipientToFile(arr1[0], arr1[1]);

					System.out.println("Recipient was added successfully");
				} else
					System.out.println("Recipient not added sucessfully."
										+" Invalid Details: email");
			} else if (arr1[0].equalsIgnoreCase("Office_friend") && arr2.length == 4) {
				if (DateValidator.isValidDate(arr2[3].strip()) 
						&& EmailValidator.isValidEmail(arr2[1].strip())) {
					// code to add a new recipient to ArrayList of recipients
					OfficeFriend tem = new OfficeFriend(arr2[0].strip(), arr2[1].strip(), 
														arr2[2].strip(), arr2[3].strip());
					recipients.add(tem);

					// store details in clientList.txt file
					FilesHandler.addRecipientToFile(arr1[0], arr1[1]);

					System.out.println("Recipient was added successfully");
				} else
					System.out.println("Recipient not added sucessfully."
										+" Invalid Details: email OR birthday");
			} else if (arr1[0].equalsIgnoreCase("Personal") && arr2.length == 4) {
				if (DateValidator.isValidDate(arr2[3].strip()) 
						&& EmailValidator.isValidEmail(arr2[2].strip())) {
					// code to add a new recipient to ArrayList of recipients
					recipients.add(new Personal(arr2[0].strip(), arr2[1].strip(), 
												arr2[2].strip(), arr2[3].strip()));

					// store details in clientList.txt file
					FilesHandler.addRecipientToFile(arr1[0], arr1[1]);

					System.out.println("Recipient was added successfully");
				} else
					System.out.println("Recipient not added sucessfully."
										+" Invalid Details: email OR birthday");
			} else
				System.out.println("Recipient not added sucessfully. Invalid Details");
		} else
			System.out.println("Recipient not added sucessfully. Invalid Details");
	}

	// -------------------------------------------------------------------------------------------------
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> MAIN-FUNCTION <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	// -------------------------------------------------------------------------------------------------
	public static void main(String[] args) 
						throws IOException, ClassNotFoundException,InputMismatchException {
		// to have all Recipient objects in our EmailClient
		ArrayList<Recipient> recipients = new ArrayList<Recipient>();
		// to have the recipients who having birthday today
		ArrayList<IBirthday> birthdayToday = new ArrayList<IBirthday>();

		// Start email client by userInput
		while (true) {
			System.out.println("To start email client enter --> start");
			String startCommand = (new Scanner(System.in)).nextLine();
			if (startCommand.equalsIgnoreCase("start"))
				break;
		}
		startEmailClient(recipients, birthdayToday);
		System.out.println("Email_Client had started successfully.");
		
		boolean exitClient=false;
		
		while (!exitClient) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nEnter option type you want to perform: \n"
					+ "1 - Adding a new recipient to Email Client & clientList.txt \n" 
					+ "2 - Sending an custom email\n"
					+ "3 - Printing out all the recipients who have birthday on given date\n"
					+ "4 - Printing out the details of emails which sent on a certain date\n"
					+ "5 - Printing out the number of recipient objects in the application\n"
					+ "-1 - For Close Email Client\n"
					+ "------------- DO NOT ENTER ANYTHING OTHER THAN INTEGERS -------------");

			int option = scanner.nextInt();

			switch (option) {
			case 1:
				while (true) {
					addNewRecipient(recipients);
					
					System.out.println("\nEnter 1 for add another recipient,"
										+" else enter any other integer:");
					int option2 = scanner.nextInt();
					if (option2 != 1)
						break;
				}
				break;
				
			case 2:
				while (true) {
//					System.out.println("Enter the mail you wish to sent in the following format: "
//							+"<email_address(TO)>,<Subject>,<Content>"
//							+"\n-----DON'T LEAVE ANY WHITE SPACES OR UNWANTED CHARACTERS WITHIN THE ENTRY-----");
//					
//					String mail = (new Scanner(System.in)).nextLine();
//					Email email = EmailSender.sendCustomMail_TO_only(mail);
					Email email = EmailSender.sendCustomMail();
					FilesHandler.updateEmailsSentEntry(email);
					
					System.out.println("\nEnter 1 for send another mail,"
							+" else enter any other integer:");
					int option2 = scanner.nextInt();
					if (option2 != 1)
						break;
				}		
				break;
				
			case 3:
				System.out.println("\nEnter the date to get the Recipients,"
									+" who have birthday on that date:"
									+" (InputFormat --> yyyy/MM/dd (e.g.: 2018/09/17))");
				
				String date = (new Scanner(System.in)).nextLine();
				
				if (DateValidator.isValidDate(date.strip())) {
					boolean con1 = false; 
					// condition represents, is the first person having birthday found
					for (Recipient r : recipients) {
						if (r instanceof IBirthday) {
							String name = r.getName();
							IBirthday b = (IBirthday) r;
							if (DateValidator.isDateBoD(b.getBirthday(), date)){
								if (!con1) {
									System.out.println("The Recipients who have birthday on "
														+date+"are,");
									con1 = true;
								}
								System.out.println("\t\t"+name + ">>> DoB :" + b.getBirthday());
							}
						}
					}
					if (!con1)
						System.out.println("There are no Recipients birthday on: "+date);
				}
				else
					System.out.println("Invalid date given.");
				break;
				
			case 4:
				HashMap<String, ArrayList<Email>> sentEmails = FilesHandler.getEmailsSentEntry();

				if (sentEmails.isEmpty())
					System.out.println("There were no emails sent yet.");
				else {
					System.out.println("Enter the date to get the "
										+"details of mails sent on particular date: "
										+ "(InputFormat --> yyyy/MM/dd (e.g.: 2018/09/17))");
					
					String date2 = (new Scanner(System.in)).nextLine();
					
					if (DateValidator.isValidDate(date2.strip())) {
						if (sentEmails.containsKey(date2.strip())) {
							System.out.println("Emails sent on "+date2.strip()+ "\n");

							// code to print the details of emails sent on the input date
							for (Email e : sentEmails.get(date2.strip())) {
								e.printMail();
							}
						} else
							System.out.println("No emails was sent on "+date2.strip()+" \n");
					} else
						System.out.println("Invalid date given.\n");
				}
				break;
				
			case 5:
				System.out.println("Number of recipient objects in the application: " 
									+ Recipient.count);
				break;
				
			case -1:
				exitClient=true;
				System.out.println("Successfully Closed Email Client");
				break;
			}			
			}
	}
	// -------------------------------------------------------------------------------------------------

}
