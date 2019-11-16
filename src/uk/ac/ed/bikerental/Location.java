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
    
    // You can add your own methods here
}
