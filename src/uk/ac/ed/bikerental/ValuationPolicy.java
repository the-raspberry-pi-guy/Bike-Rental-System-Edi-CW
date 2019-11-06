package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ValuationPolicy {
	public default BigDecimal calculateValue(Bike bike, LocalDate date) {
		return bike.getType().getReplacementValue();
	}
}
