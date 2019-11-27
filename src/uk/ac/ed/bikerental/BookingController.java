package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

public class BookingController implements DeliveryService {

	private HashSet<Booking> bookingList;
	private BikeProvider provider;
	private HashMap<Bike, LocalDate> deliveries;

	public BookingController(BikeProvider provider) {
		this.provider = provider;
		bookingList = new HashSet<Booking>();
	}

	public HashSet<Booking> getBookingList() {
		return bookingList;
	}

	@Override
	public void scheduleDelivery(Deliverable bike, Location pickupLocation, Location dropoffLocation,
			LocalDate pickupDate) {
		deliveries.put((Bike) bike, pickupDate);
	}
	
}
