package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.UUID;

public class Booking {

	private UUID orderNo;
	private DateRange hireDates;
	private boolean requiresDelivery;
	private BigDecimal totalPrice;
	private BigDecimal depositAmount;
	private boolean depositPaid;

	public Booking(DateRange hireDates, boolean requiresDelivery, BigDecimal totalPrice, BigDecimal depositAmount) {
		this.hireDates = hireDates;
		this.requiresDelivery = requiresDelivery;
		this.totalPrice = totalPrice;
		this.depositAmount = depositAmount;
		this.orderNo = UUID.randomUUID();
		this.depositPaid = false;
	}
}
