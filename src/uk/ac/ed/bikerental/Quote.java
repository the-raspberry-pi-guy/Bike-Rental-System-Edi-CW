package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/*
 * Represents a single quote in the system, containing all information that the customer has entered to narrow down
 * their results. Quotes do not affect the rest of the system like Bookings.
 */

public class Quote {
	
	private DateRange bookingRange;
	private BikeProvider provider;
	private HashSet<Bike> bikeList;
	private BigDecimal totalPrice;
	private BigDecimal totalDeposit;

	public Quote(DateRange range, BikeProvider provider, HashSet<Bike> bikeList, BigDecimal totalPrice, BigDecimal totalDeposit) {
		this.bookingRange = range;
		this.provider = provider;
		this.bikeList = bikeList;
		this.totalPrice = totalPrice;
		this.totalDeposit = totalDeposit;
	}

    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public DateRange getBookingRange() {
        return bookingRange;
    }

    public BikeProvider getProvider() {
        return provider;
    }

    public HashSet<Bike> getBikeList() {
        return bikeList;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Quote [bookingRange=" + bookingRange + ", provider=" + provider + ", bikeList=" + bikeList
                + ", totalPrice=" + totalPrice + ", totalDeposit=" + totalDeposit + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(bikeList, bookingRange, provider, totalDeposit, totalPrice);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Quote other = (Quote) obj;
        return Objects.equals(bikeList, other.bikeList) && Objects.equals(bookingRange, other.bookingRange)
                && Objects.equals(provider, other.provider) && Objects.equals(totalDeposit, other.totalDeposit)
                && Objects.equals(totalPrice, other.totalPrice);
    }
}
