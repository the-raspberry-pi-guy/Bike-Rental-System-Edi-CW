package uk.ac.ed.bikerental;

public class Location {
    private String postcode;
    private String address;
    
    public Location(String postcode, String address) {
        assert postcode.length() >= 6; // Catches postcodes that are not complete
        this.postcode = postcode;
        this.address = address;
    }
    
    public boolean isNearTo(Location other) {
        if(this.postcode.substring(0, 2).equals(other.postcode.substring(0, 2))) {
            return true;
        } else {
        	return false;
        }
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAddress() {
        return address;
    }
    

    @Override
    public String toString() {
        return "Location [postcode=" + postcode + ", address=" + address + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
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
        Location other = (Location) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (postcode == null) {
            if (other.postcode != null)
                return false;
        } else if (!postcode.equals(other.postcode))
            return false;
        return true;
    }
    
    // You can add your own methods here
}
