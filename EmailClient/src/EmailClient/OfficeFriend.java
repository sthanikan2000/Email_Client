package EmailClient;

public class OfficeFriend extends Official implements IBirthday{
	private String birthday;
	
	public OfficeFriend(String name, String email, String designation, String birthday){
		super(name,email,designation);
		this.birthday=birthday;
	}	
	
	@Override
	public String getBirthday() {
		return birthday;
	}	
	
	@Override
	public String getBirthdayGreeting() {
		return ("Wish you a Happy Birthday.\n\n-regards\nThanikan");
	}	
}
