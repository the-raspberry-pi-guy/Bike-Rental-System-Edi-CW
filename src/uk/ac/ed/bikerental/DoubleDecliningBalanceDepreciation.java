package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DoubleDecliningBalanceDepreciation implements ValuationPolicy {
    private BigDecimal depreciationRate;
    
    public DoubleDecliningBalanceDepreciation(BigDecimal rate) {
        depreciationRate = rate;
    }
    
    public BigDecimal calculateValue(Bike bike, LocalDate date) {
        int yearSinceBikePurchase;
        BigDecimal calculatedValue;
        BigDecimal depreciationDecimalRate;
		BigDecimal doubleDepreciationRate;
        
        // Calculate the years since the bike was purchased
        yearSinceBikePurchase = date.getYear() - bike.getDateOfPurchase().getYear();
        // Calculate the depreciation rate as a decimal value
        depreciationDecimalRate = depreciationRate.divide(new BigDecimal("100"));
        doubleDepreciationRate = depreciationDecimalRate.multiply(new BigDecimal("2"));
                
        // Formula for the double declining balance depreciation rate: VAL * 0.RATE^YEAR
        BigDecimal doubleDepreciationRateOut1 = new BigDecimal("1").subtract(doubleDepreciationRate);
        BigDecimal doubleDepreciationRateOut1ToYearPower = doubleDepreciationRateOut1.pow(yearSinceBikePurchase);
        calculatedValue = bike.getType().getReplacementValue().multiply(doubleDepreciationRateOut1ToYearPower);
        return calculatedValue;
	}
}
