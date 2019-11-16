package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bike {
	
	private ArrayList<DateRange> datesBooked;
	private BikeType type;
	private LocalDate dateOfPurchase;

	public Bike(BikeType type, LocalDate date) {
		this.type = type;
		this.dateOfPurchase = date;
		datesBooked = new ArrayList<DateRange>();
	}
	
    public BikeType getType() {
        return type;
    }
    
    public LocalDate getDateOfPurchase() {
    	return dateOfPurchase;
    }
    
    public String toString() {
        return String.format("%s %s", type.getBikeType(), dateOfPurchase);
    }
    
    public Boolean isAvailable(DateRange other) {
        // Tests if any of the dates in a range clash when the bike is not available
        for (DateRange range: datesBooked) {
    		if(range.overlaps(other)) {
    			return false;
    		}
    	}
    	return true;
    } 
    
    public void makeUnavailable(DateRange range) {
    	datesBooked.add(range);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateOfPurchase == null) ? 0 : dateOfPurchase.hashCode());
        result = prime * result + ((datesBooked == null) ? 0 : datesBooked.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Bike other = (Bike) obj;
        if (dateOfPurchase == null) {
            if (other.dateOfPurchase != null)
                return false;
        } else if (!dateOfPurchase.equals(other.dateOfPurchase))
            return false;
        if (datesBooked == null) {
            if (other.datesBooked != null)
                return false;
        } else if (!datesBooked.equals(other.datesBooked))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
    
}