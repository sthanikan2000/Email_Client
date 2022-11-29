package EmailClient;

public class Personal extends Recipient implements IBirthday{
	private String nickName;
	private String birthday;
	
	public Personal(String name, String nickName, String email, String birthday){
		super(name,email);
		this.nickName=nickName;
		this.birthday=birthday;
	}
	
	public String getNickName() {
		return nickName;
	}	
	
	@Override
	public String getBirthday() {
		return birthday;
	}
	
	@Override
	public String getBirthdayGreeting() {
		return ("Hugs and love on your birthday.\n\n-regards\nThanikan");
	}
}
