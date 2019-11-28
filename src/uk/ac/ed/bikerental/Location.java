package uk.ac.ed.bikerental;

/**
 * Location is a class that represents and stores the location of either a customer or
 * a provider. A location object encapsulates the necessary location information to allow
 * for comparisons of distance, a postcode and address. It is implemented as a small,
 * independent class that can be utilised for many projects.
 * 	
 */
public class Location {

    private String postcode;
    private String address;
    
    /**
     * Creates a new location object with the given postcode and address, and will
     * catch any postcodes that are more than 6 characters.
     * @param postcode The postcode of the location
     * @param address The address of the location
     */
    public Location(String postcode, String address) {
        assert postcode.length() >= 6; // Catches postcodes that are not complete
        this.postcode = postcode;
        this.address = address;
    }
    
    /**
     * Compares two locations by looking at their postcodes. We assume that two locations
     * are nearby if the first two characters of their postcodes are equal, and return
     * true if they are, or false if they are not.
     * @param other The location we are comparing to. 
     * @return A boolean value of whether or not the locations are nearby each other.
     */
    public boolean isNearTo(Location other) {
        if(this.postcode.substring(0, 2).equals(other.postcode.substring(0, 2))) {
            return true;
        } else {
        	return false;
        }
    }

    /**
     * Returns the postcode of the location object.
     * @return The postcode of the location.
     */
    public String getPostcode() {
        return postcode;
    }
    
    /**
     * Returns the address of the location object.
     * @return The address of the location.
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * Implements the toString method for a location object. Useful for both debugging
     * purposes, or for displaying a location in a simple format.
     * @return A string displaying the properties of the location object.
     */
    @Override
    public String toString() {
        return "Location [postcode=" + postcode + ", address=" + address + "]";
    }

    /**
     * Implements the hashCode method for locations, allowing for them to be used in
     * data types that rely on this function.
     * @return A unique hashcode for the object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
        return result;
    }
    /**
     * Implements the equals function for locations, allowing for direct comparisons
     * between two location objects. Two locations are equal if they have the same
     * address and postcode.
     * @param obj The object to compare to.
     * @return A boolean value of whether or not the two objects are equal.
     */
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

}
