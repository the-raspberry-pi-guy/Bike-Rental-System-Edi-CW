package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public class Booking {

	private UUID orderNo;
	private DateRange hireDates;
	private boolean requiresDelivery;
	private BigDecimal totalPrice;
	private BigDecimal depositAmount;
	private boolean depositPaid;
	private BikeProvider hireProvider;
	private ArrayList<Bike> bikeList;
	private Customer customer;

	public Booking(DateRange hireDates, boolean requiresDelivery, BigDecimal totalPrice, BigDecimal depositAmount, ArrayList<Bike> bikeList, BikeProvider provider, Customer customer) {
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

	public ArrayList<Bike> getBikeList() {
		return bikeList;
	}

	public Customer getCustomer() {
		return customer;
	}
}
