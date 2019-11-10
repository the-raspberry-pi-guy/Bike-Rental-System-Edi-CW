.package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Bike {
    public LocalDate dateOfPurchase;
	public BikeType bikeType;
    
    // Constructor to set attributes
    public Bike(String type, BigDecimal replacementValue, LocalDate date) {
        dateOfPurchase = date;
        bikeType = new BikeType(type, replacementValue);
    }
	
    public String getType() {
        return bikeType.getBikeType();
    }
}