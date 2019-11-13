package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class BikeType {
	
	private String typeModel;
	private BigDecimal replacementValue;

	public BikeType(String name, BigDecimal replacementValue) {
		this.typeModel = name;
		this.replacementValue = replacementValue;
	}
	
    public BigDecimal getReplacementValue() {
        return replacementValue;
    }
    
    public String getBikeType() {
        return typeModel;
    }
    
}