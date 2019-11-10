package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class BikeType {
    private BigDecimal replacementValue;
    private String type;
    
    public BikeType(String typeModel, BigDecimal replacementVal) {
        replacementValue = replacementVal;
        type = typeModel;
    }
    
    public BigDecimal getReplacementValue() {
        return replacementValue;
    }
    
    public String getBikeType() {
        return type;
    }
}