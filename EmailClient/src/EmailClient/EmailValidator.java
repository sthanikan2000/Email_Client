package EmailClient;

import java.util.regex.Pattern;

public class EmailValidator {
		//The following method will check whether the String can be considered as a email address
		public static boolean isValidEmail(String email){
			
			//Properties of design a valid mail address
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." 
								+ "[a-zA-Z0-9_+&*-]+)*@" 
								+ "(?:[a-zA-Z0-9-]+\\.)+[a-z"
								+ "A-Z]{2,7}$";

			Pattern pat = Pattern.compile(emailRegex);
			if (email == null)
				return false;
			//IF the properties matches :return true
			//i.e., the above string satisfies the properties of email address
			return pat.matcher(email).matches();
		}
}
