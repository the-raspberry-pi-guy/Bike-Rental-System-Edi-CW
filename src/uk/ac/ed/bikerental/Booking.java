package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

public class Booking {

	private final UUID orderNo;
	private final DateRange hireDates;
	private final boolean requiresDelivery;
	private final BigDecimal totalPrice;
	private final BigDecimal depositAmount;
	private final boolean depositPaid;
	private final BikeProvider hireProvider;
	private final HashSet<Bike> bikeList;
	private final Customer customer;

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
		
		if(requiresDelivery) {
			for(Bike bike: bikeList) {
				DeliveryServiceFactory.getDeliveryService().scheduleDelivery(bike, provider.getContactDetails().getLocation(), customer.getCustomerDetails().getLocation(), hireDates.getStart());
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
}
