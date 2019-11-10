package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.Collection;

public class Bike {
	
	private Collection<DateRange> datesBooked;
	private BikeType type;
	private LocalDate dateOfPurchase;

	public Bike(BikeType type, LocalDate date) {
		this.type = type;
		this.dateOfPurchase = date;
	}
	
    public BikeType getType() {
        return type;
    }
    
    public LocalDate getDateOfPurchase() {
    	return dateOfPurchase;
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