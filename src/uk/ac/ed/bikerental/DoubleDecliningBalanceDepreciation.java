package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DoubleDecliningBalanceDepreciation implements ValuationPolicy {
    private BigDecimal depreciationRate;
    
    public DoubleDecliningBalanceDepreciation(BigDecimal rate) {
        depreciationRate = rate;
    }
    
    public BigDecimal calculateValue(Bike bike, LocalDate date) {
        return null;
    }
}
