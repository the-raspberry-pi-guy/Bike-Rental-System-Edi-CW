package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.Collection;

public class Bike {
	
	private ArrayList<DateRange> datesBooked;
	private BikeType type;

	public Bike(BikeType type) {
		this.type = type;
		datesBooked = new ArrayList<DateRange>();
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