package uk.ac.ed.bikerental;

import java.util.ArrayList;

public class BookingController {

	private ArrayList<Booking> bookingList;
	private BikeProvider provider;

	public BookingController(BikeProvider provider) {
		this.provider = provider;
		bookingList = new ArrayList<Booking>();
	}

	public ArrayList<Booking> getBookingList() {
		return bookingList;
	}
	
}
