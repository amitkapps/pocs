package poc.hibernate.emp.vo;

public class Mail extends Contact{
	
	String primaryEmailAddress;
	String secondaryEmailAddress;
	
	public Mail() {
		// TODO Auto-generated constructor stub
	}

	
	
	public Mail(String primaryEmailAddress, String secondaryEmailAddress) {
		super();
		this.primaryEmailAddress = primaryEmailAddress;
		this.secondaryEmailAddress = secondaryEmailAddress;
	}



	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}
	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}
	public String getSecondaryEmailAddress() {
		return secondaryEmailAddress;
	}
	public void setSecondaryEmailAddress(String secondaryEmailAddress) {
		this.secondaryEmailAddress = secondaryEmailAddress;
	}
}
