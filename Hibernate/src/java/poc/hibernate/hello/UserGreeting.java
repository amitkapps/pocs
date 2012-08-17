package poc.hibernate.hello;

public class UserGreeting {

	private Long userId;
	private String greeting;
	private String userName;
	private long locationId;
	
	public String getGreeting() {
		return greeting;
	}
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	public String toString() {
		return "{" + userId + "," + greeting + "," + userName + "," + locationId + "}";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	
}
