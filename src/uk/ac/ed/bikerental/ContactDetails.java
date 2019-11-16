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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((phoneNo == null) ? 0 : phoneNo.hashCode());
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
        ContactDetails other = (ContactDetails) obj;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (phoneNo == null) {
            if (other.phoneNo != null)
                return false;
        } else if (!phoneNo.equals(other.phoneNo))
            return false;
        return true;
    }
}
