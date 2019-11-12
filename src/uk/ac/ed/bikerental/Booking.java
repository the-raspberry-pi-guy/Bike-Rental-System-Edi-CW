package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.UUID;

public class Booking {

	private UUID orderNo;
	private boolean requireDelivery;
	private boolean partnerReturn;
	private BigDecimal depositAmount;
	private Quote bookedQuote;
	
	public Booking(Quote quote, boolean requireDelivery, boolean partnerReturn) {
	    this.bookedQuote = quote;
	    this.requireDelivery = requireDelivery;
	    this.partnerReturn = partnerReturn;
	    
	    orderNo = UUID.randomUUID();
	}
}
