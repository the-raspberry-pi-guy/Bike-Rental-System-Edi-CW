package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Collection;

public class MultidayDiscountPolicy implements PricingPolicy {

	private BikeProvider provider;

	public MultidayDiscountPolicy(BikeProvider provider) {
		this.provider = provider;
	}

	@Override
	public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
		provider.setTypePrice(bikeType, dailyPrice);
	}

	
	// Calculate a price based on the number of days booked, returning the correct percentage of the total value;
	@Override
	public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
		BigDecimal discount = new BigDecimal(0);
		BigDecimal totalPrice = new BigDecimal(0);
		if(duration.toDays() <= 2) {
			discount = discount.add(new BigDecimal(1));
		} else if(duration.toDays() <= 6) {
			discount = discount.add(new BigDecimal(0.95));
		} else if(duration.toDays() <= 13) {
			discount = discount.add(new BigDecimal(0.9));
		} else if(duration.toDays() >= 14) {
			discount = discount.add(new BigDecimal(0.85));
		}
		return discount.setScale(6,BigDecimal.ROUND_DOWN); // Remove any trailing numbers
	}
	
}
