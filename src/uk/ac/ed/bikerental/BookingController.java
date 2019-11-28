package uk.ac.ed.bikerental;


import java.util.HashSet;
import java.util.Objects;

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
