package uk.ac.ed.bikerental;

import java.util.Collection;

public class Bike {
	
	private Collection<DateRange> datesBooked;
	private BikeType type;

	public Bike(BikeType type) {
		this.type = type;
	}
	
    public BikeType getType() {
        return type;
    }
    
    public Boolean isAvailable(DateRange other) {
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
    
}