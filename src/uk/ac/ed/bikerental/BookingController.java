package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class BookingController implements DeliveryService {

	private HashSet<Booking> bookingList;
	private HashMap<Bike, LocalDate> deliveries;

	public BookingController() {
		bookingList = new HashSet<Booking>();
		this.deliveries = new HashMap<Bike, LocalDate>();
	}

	public HashSet<Booking> getBookingList() {
		return bookingList;
	}

	@Override
	public void scheduleDelivery(Deliverable bike, Location pickupLocation, Location dropoffLocation,
			LocalDate pickupDate) {
		deliveries.put((Bike) bike, pickupDate);
	}

    @Override
    public int hashCode() {
        return Objects.hash(bookingList, deliveries);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookingController other = (BookingController) obj;
        return Objects.equals(bookingList, other.bookingList) && Objects.equals(deliveries, other.deliveries);
    }
	
}
