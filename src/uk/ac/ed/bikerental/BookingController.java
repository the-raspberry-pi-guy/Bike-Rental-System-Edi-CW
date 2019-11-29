package uk.ac.ed.bikerental;


import java.util.HashSet;
import java.util.Objects;

/*
 * A BookingController holds all bookings for a given provider that it belongs to, allowing for easy access
 * to the details of any particular booking.
 */

public class BookingController {

	private HashSet<Booking> bookingList;

	public BookingController() {
		bookingList = new HashSet<Booking>();
	}

	public HashSet<Booking> getBookingList() {
		return bookingList;
	}

    @Override
    public int hashCode() {
        return Objects.hash(bookingList);
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
        return Objects.equals(bookingList, other.bookingList);
    }
	
}
