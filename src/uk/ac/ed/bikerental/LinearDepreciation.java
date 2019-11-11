package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LinearDepreciation implements ValuationPolicy {
    private BigDecimal depreciationRate;
    
    public LinearDepreciation(BigDecimal rate) {
        depreciationRate = rate;
    }
    
    public BigDecimal calculateValue(Bike bike, LocalDate date) {
		
		// Calculate the years since the bike was purchased
		long yearSinceBikePurchase = ChronoUnit.YEARS.between(bike.getDateOfPurchase(),date);
		
		// Calculate the depreciation rate as a decimal value
		BigDecimal depreciationDecimalRate = depreciationRate.divide(new BigDecimal("100")); 
		
		// Formula for the linear depreciation, to calculate the value lost over years
        BigDecimal totalPercentageRate = new BigDecimal(yearSinceBikePurchase).multiply(depreciationDecimalRate);
        BigDecimal lostValue = totalPercentageRate.multiply(bike.getType().getReplacementValue());
        
        // Subtract the lost value from the bike type's replacement value
		BigDecimal calculatedValue = bike.getType().getReplacementValue().subtract(lostValue); 
        
		// If depreciated past £0, then just set the calculated deposit to £0 also
		if (calculatedValue.compareTo(BigDecimal.ZERO) == -1) {
            calculatedValue = BigDecimal.ZERO;
        }
        
		return calculatedValue;
	}
}
