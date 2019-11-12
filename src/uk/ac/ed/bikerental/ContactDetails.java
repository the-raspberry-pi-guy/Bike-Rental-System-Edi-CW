package uk.ac.ed.bikerental;

public class ContactDetails {
	
	private Location location;
	private int phoneNo;

	public ContactDetails(Location location, int phoneNo) {
		this.location = location;
		this.phoneNo = phoneNo;
	}
	
	public Location getLocation() {
	    return location;
	}
	
	public int getPhoneNo() {
	    return phoneNo;   
	}
}
