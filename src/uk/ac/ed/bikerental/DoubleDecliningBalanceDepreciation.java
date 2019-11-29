package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/*
 * Represents a given ValuationPolicy, in this case DoubleDecliningBalanceDepreciation. This can be implemented to
 * enforce this policy, calculating prices based on the double declining balance depreciation formula defined in this 
 * class rather than the default calculations in BikeProvider.
 */

public class DoubleDecliningBalanceDepreciation implements ValuationPolicy {
    private BigDecimal depreciationRate;
    
    public DoubleDecliningBalanceDepreciation(BigDecimal rate) {
        this.depreciationRate = rate;
    }
    
    public BigDecimal calculateValue(Bike bike, LocalDate date) {
        
        // Calculate the years since the bike was purchased
        long yearSinceBikePurchase = ChronoUnit.YEARS.between(bike.getDateOfPurchase(), date);
        
        // Calculate the depreciation rate as a decimal value
        BigDecimal depreciationDecimalRate = depreciationRate.divide(new BigDecimal("100")); 
        // Double the depreciation rate for the Double Declining Balance Depreciation formula
        BigDecimal doubleDepreciationRate = depreciationDecimalRate.multiply(new BigDecimal("2"));
                
        // Formula for the double declining balance depreciation rate: VAL * 0.RATE^YEAR
        BigDecimal doubleDepreciationRateOut1 = new BigDecimal("1").subtract(doubleDepreciationRate);
        BigDecimal doubleDepreciationRateOut1ToYearPower = doubleDepreciationRateOut1.pow((int) yearSinceBikePurchase);
        
        // Multiply the bike's replacement value by the remaining rate after depreciation
        BigDecimal calculatedValue = bike.getType().getReplacementValue().multiply(doubleDepreciationRateOut1ToYearPower);
        
        // If depreciated past £0, then just set the calculated deposit to £0 also
        if (calculatedValue.compareTo(BigDecimal.ZERO) == -1) {
            calculatedValue = BigDecimal.ZERO;
        }
        
        return calculatedValue;
	}
}
