package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LinearDepreciation implements ValuationPolicy {
    private BigDecimal depreciationRate;
    
    public LinearDepreciation(BigDecimal rate) {
        depreciationRate = rate;
    }
    
    public BigDecimal calculateValue(Bike bike, LocalDate date) {
		int yearSinceBikePurchase;
		BigDecimal calculatedValue;
		BigDecimal depreciationDecimalRate;
		
		// Calculate the years since the bike was purchased
		yearSinceBikePurchase = date.getYear() - bike.getDateOfPurchase().getYear();
		// Calculate the depreciation rate as a decimal value
		depreciationDecimalRate = depreciationRate.divide(new BigDecimal("100")); 
		
		
		// Formula for the linear depreciation rate
        BigDecimal totalPercentageRate = new BigDecimal(yearSinceBikePurchase).multiply(depreciationDecimalRate);
        BigDecimal lostValue = totalPercentageRate.multiply(bike.getType().getReplacementValue());
        
		calculatedValue = bike.getType().getReplacementValue().subtract(lostValue); 
        return calculatedValue;
	}
}
