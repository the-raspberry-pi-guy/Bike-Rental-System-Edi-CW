package uk.ac.ed.bikerental;

public class Customer {
	
	private String firstName;
	private String surname;
	private ContactDetails customerDetails;
	private boolean deleteFlag;

	public Customer(String firstName, String surname, ContactDetails customerDetails, boolean deleteFlag) {
		this.firstName = firstName;
		this.surname = surname;
		this.customerDetails = customerDetails;
		this.deleteFlag = deleteFlag;
	}
}