package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class Quote {
	
	private DateRange bookingRange;
	private BikeProvider provider;
	private ArrayList<Bike> bikeList;
	private BigDecimal totalPrice;

	public Quote(DateRange range, BikeProvider provider, ArrayList<Bike> bikeList, BigDecimal totalPrice) {
		this.bookingRange = range;
		this.provider = provider;
		this.bikeList = bikeList;
		this.totalPrice = totalPrice;
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
    
    public String toString() {
        return ("Quote: " + bookingRange.toString() + " " + provider.toString() + " " + bikeList.toString()
                        + " " + totalPrice.toPlainString());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bikeList == null) ? 0 : bikeList.hashCode());
        result = prime * result + ((bookingRange == null) ? 0 : bookingRange.hashCode());
        result = prime * result + ((provider == null) ? 0 : provider.hashCode());
        result = prime * result + ((totalPrice == null) ? 0 : totalPrice.hashCode());
        return result;
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
        if (bikeList == null) {
            if (other.bikeList != null)
                return false;
        } else if (!bikeList.equals(other.bikeList))
            return false;
        if (bookingRange == null) {
            if (other.bookingRange != null)
                return false;
        } else if (!bookingRange.equals(other.bookingRange))
            return false;
        if (provider == null) {
            if (other.provider != null)
                return false;
        } else if (!provider.equals(other.provider))
            return false;
        if (totalPrice == null) {
            if (other.totalPrice != null)
                return false;
        } else if (!totalPrice.equals(other.totalPrice))
            return false;
        return true;
    }
}
