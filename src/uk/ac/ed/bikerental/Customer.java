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

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public ContactDetails getCustomerDetails() {
        return customerDetails;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customerDetails == null) ? 0 : customerDetails.hashCode());
        result = prime * result + (deleteFlag ? 1231 : 1237);
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (customerDetails == null) {
            if (other.customerDetails != null)
                return false;
        } else if (!customerDetails.equals(other.customerDetails))
            return false;
        if (deleteFlag != other.deleteFlag)
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        return true;
    }
	
}
