package EmailClient;

public abstract class Recipient {
	private String name;
	private String email;
	public static int count=0;
	
	public Recipient(String name,String email) {
		this.name=name;
		this.email=email;
		count++;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
}
