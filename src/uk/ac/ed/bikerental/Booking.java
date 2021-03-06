package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

import uk.ac.ed.bikerental.Bike.Status;

/*
 * A Booking represents a single booking within the system, and holds all necessary information regarding that booking.
 * A booking is only ever created when a customer desires to book a given quote, and implies that payment has been made.
 * Booking implements the Deliverable interface, as the DeliveryService will handle delivering all bikes associated with
 * a given booking, which are held here under bikeList.
 */

public class Booking implements Deliverable{

    private final UUID orderNo;
	private final DateRange hireDates;
	private final boolean requiresDelivery;
	private final BigDecimal totalPrice;
	private final BigDecimal depositAmount;
	private boolean depositPaid;
	private final BikeProvider hireProvider;
	private final HashSet<Bike> bikeList;
	private final Customer customer;
	private Status bookingStatus;
	
    enum Status {
        UNCOMPLETE, // When booking has been made and bikes are not in the store
        IN_TRANSIT_TO_CUSTOMER, // On its way to the customer
        IN_TRANSIT_TO_STORE, // Coming back from customer
        BIKES_WITH_CUSTOMER, // To be set to this when bikes are delivered
        COMPLETE // When the bikes have been returned
    }

	public Booking(DateRange hireDates, boolean requiresDelivery, BigDecimal totalPrice, BigDecimal depositAmount, HashSet<Bike> bikeList, BikeProvider provider, Customer customer) {
		this.hireDates = hireDates;
		this.requiresDelivery = requiresDelivery;
		this.totalPrice = totalPrice;
		this.depositAmount = depositAmount;
		this.orderNo = UUID.randomUUID();
		this.depositPaid = false;
		this.hireProvider = provider;
		this.bikeList = bikeList;
		this.customer = customer;
		this.bookingStatus = Status.UNCOMPLETE;
	}
	
    @Override
    public String toString() {
        return "Booking [orderNo=" + orderNo + ", hireDates=" + hireDates + ", requiresDelivery=" + requiresDelivery
                + ", totalPrice=" + totalPrice + ", depositAmount=" + depositAmount + ", depositPaid=" + depositPaid
                + ", hireProvider=" + hireProvider + ", bikeList=" + bikeList + ", customer=" + customer
                + ", bookingStatus=" + bookingStatus + "]";
    }

    @Override
    public void onPickup() {
        
        // Change the status of the booking
        if (bookingStatus == Booking.Status.UNCOMPLETE) {
            if (this.requiresDelivery) bookingStatus = Booking.Status.IN_TRANSIT_TO_CUSTOMER;
            else bookingStatus = Booking.Status.BIKES_WITH_CUSTOMER;
        } else if (bookingStatus == Booking.Status.BIKES_WITH_CUSTOMER) {
            bookingStatus = Booking.Status.IN_TRANSIT_TO_STORE;
        }
        
        // Change the status of all of the bikes in the booking
        for (Bike bike:bikeList) {
            if(bike.getBikeStatus() == Bike.Status.IN_STORE) {
                if (this.requiresDelivery) bike.setBikeStatus(Bike.Status.IN_TRANSIT_TO_CUSTOMER);
                else bike.setBikeStatus(Bike.Status.WITH_CUSTOMER);
            } else if(bike.getBikeStatus() == Bike.Status.WITH_CUSTOMER) {
                bike.setBikeStatus(Bike.Status.IN_TRANSIT_TO_STORE);
            }
        }
    }

    @Override
    public void onDropoff() {
        
        // Change the status of the booking
        if (bookingStatus == Booking.Status.IN_TRANSIT_TO_CUSTOMER) {
            bookingStatus = Booking.Status.BIKES_WITH_CUSTOMER;
        } else if (bookingStatus == Booking.Status.IN_TRANSIT_TO_STORE) {
            bookingStatus = Booking.Status.COMPLETE;
        } else if (bookingStatus == Booking.Status.BIKES_WITH_CUSTOMER) {
            bookingStatus = Booking.Status.COMPLETE;
        }
        
        // Change the status of all of the bikes in the booking
        for (Bike bike:bikeList) {
            if(bike.getBikeStatus() == Bike.Status.IN_TRANSIT_TO_CUSTOMER) {
                bike.setBikeStatus(Bike.Status.WITH_CUSTOMER);
            } else if(bike.getBikeStatus() == Bike.Status.IN_TRANSIT_TO_STORE) {
                bike.setBikeStatus(Bike.Status.IN_STORE);
            } else if(bike.getBikeStatus() == Bike.Status.WITH_CUSTOMER) {
                bike.setBikeStatus(Bike.Status.IN_STORE);
            }
        }        
    }

	public UUID getOrderNo() {
		return orderNo;
	}

	public DateRange getHireDates() {
		return hireDates;
	}

	public boolean isRequiresDelivery() {
		return requiresDelivery;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public boolean isDepositPaid() {
		return depositPaid;
	}

	public BikeProvider getHireProvider() {
		return hireProvider;
	}

	public HashSet<Bike> getBikeList() {
		return bikeList;
	}

	public Customer getCustomer() {
		return customer;
	}

    public Status getStatus() {
        return bookingStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Booking other = (Booking) obj;
        return Objects.equals(orderNo, other.orderNo);
    }
	
}
