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
    
}