package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class Quote {
	
	private DateRange bookingRange;
	private BikeProvider provider;
	private ArrayList<Bike> bikeList;
	private BigDecimal totalPrice;

	public Quote(DateRange range, BikeProvider provider, ArrayList<Bike> bikeList) {
		this.bookingRange = range;
		this.provider = provider;
		this.bikeList = bikeList;
	}

    public DateRange getBookingRange() {
        return bookingRange;
    }

    public BikeProvider getProvider() {
        return provider;
    }

    public ArrayList<Bike> getBikeList() {
        return bikeList;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
