package EmailClient;

import java.io.*;
import java.util.*;


public class FilesHandler {
	// -------------------------------------------------------------------------------------------------	
	public static void checkSerFileExists() throws IOException {
		try {
			//check for the existing of "SentEmails.ser"
			FileInputStream fis=new FileInputStream("SentEmails.ser");
			fis.close();				
		}catch(FileNotFoundException e) {
			//if file not found create it and write new HashMap to save our email sending entries
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SentEmails.ser"));
			//HashMap<String, ArrayList<Email>> hashmap=new HashMap<String, ArrayList<Email>>();
			oos.writeObject(new HashMap<String, ArrayList<Email>>());
			oos.close();								
		}
	}
	// -------------------------------------------------------------------------------------------------	
	public static void checkTextFileExists() throws IOException{
		try {
			//check for the existing of "clientList.txt"
			FileReader fr=new FileReader("clientList.txt");
			fr.close();				
		}catch(FileNotFoundException e) {
			//if file not found create it and write new HashMap to save our email sending entries
			FileWriter fw=new FileWriter("clientList.txt");
			fw.close();
		}
	}
	
	// -------------------------------------------------------------------------------------------------	
	public static void updateEmailsSentEntry(Email email) throws IOException, ClassNotFoundException {
		if (email != null) {
			String today = DateValidator.getToday();
			
			checkSerFileExists();
			
			//Since we are deal with serialized single hashmap, 
			//we can de-serialize it using single call of readObject method
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("SentEmails.ser"));
			Object obj=ois.readObject();
			ois.close();
			
			//Down casting object to hashmap
			HashMap<String, ArrayList<Email>> sentEmails = (HashMap<String, ArrayList<Email>>) obj;
				
			if (sentEmails.containsKey(today)) {
				sentEmails.get(today).add(email);
			} else {
				ArrayList<Email> newEntry = new ArrayList<Email>();
				newEntry.add(email);
				sentEmails.put(today, newEntry);
			}
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SentEmails.ser"));
			//Overrides the already existing hash map with updated hash map
			oos.writeObject(sentEmails);
			oos.close();			
		}
	}
	// -------------------------------------------------------------------------------------------------
	public static HashMap<String, ArrayList<Email>> getEmailsSentEntry() throws IOException, ClassNotFoundException {
		checkSerFileExists();
		
		ObjectInputStream ois =
			new ObjectInputStream(new FileInputStream("SentEmails.ser"));
		HashMap<String, ArrayList<Email>> 
			sentEmails = (HashMap<String, ArrayList<Email>>) ois.readObject();
		ois.close();
		
		return sentEmails;
	}
		
	// -------------------------------------------------------------------------------------------------
	public static void addRecipientToFile(String type, String details) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("clientList.txt", true));
		writer.write("\n" + type + ":" + details);
		writer.close();
	}

	// -------------------------------------------------------------------------------------------------
	public static void getRecipientsFromFile
			(ArrayList<Recipient> recipients, ArrayList<IBirthday> birthdayToday)throws IOException {
		
		checkTextFileExists();
		
		BufferedReader br = new BufferedReader(new FileReader("clientList.txt"));
		String s;
		while ((s = br.readLine()) != null) {
			// here I assume that recipient entries in "clinetList.txt" are valid

			String[] arr1 = s.split(":");// to separate type of Recipient
			String[] arr2 = arr1[1].split(",");// Separate details of Recipient

			if (arr1[0].equalsIgnoreCase("Official")) {
				Official tem = new Official(arr2[0].strip(), arr2[1].strip(), arr2[2].strip());
				recipients.add(tem);
			} else if (arr1[0].equalsIgnoreCase("Office_Friend")) {
				OfficeFriend tem = new OfficeFriend(arr2[0].strip(), arr2[1].strip(), 
													arr2[2].strip(),arr2[3].strip());
				recipients.add(tem);

				// check whether today is birthday and add to birthdayToday
				if (DateValidator.isSameDateMonthAsToday(arr2[3])) birthdayToday.add(tem);
			}
			else {
				Personal tem = new Personal(arr2[0].strip(), arr2[1].strip(), 
											arr2[2].strip(), arr2[3].strip());
				recipients.add(tem);

				// check whether today is birthday and add to birthdayToday
				if (DateValidator.isSameDateMonthAsToday(arr2[3])) birthdayToday.add(tem);
			}
		}
		br.close();
	}	

}
