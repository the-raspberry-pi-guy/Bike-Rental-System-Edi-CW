package uk.ac.ed.bikerental;

public class ContactDetails {
	
	private Location location;
	private String phoneNo;

	public ContactDetails(Location location, String phoneNo) {
		this.location = location;
		this.phoneNo = phoneNo;
	}
	
	public Location getLocation() {
	    return location;
	}
	
	public String getPhoneNo() {
	    return phoneNo;   
	}
}
