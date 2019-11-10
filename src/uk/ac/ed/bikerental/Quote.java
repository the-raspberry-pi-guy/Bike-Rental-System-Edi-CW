package uk.ac.ed.bikerental;

import java.util.Collection;

public class Quote {
	
	private DateRange bookingRange;
	private BikeProvider provider;
	private Collection<Bike> bikeList;

	public Quote(DateRange range, BikeProvider provider, Collection<Bike> bikeList) {
		this.bookingRange = range;
		this.provider = provider;
		this.bikeList = bikeList;
	}
}
