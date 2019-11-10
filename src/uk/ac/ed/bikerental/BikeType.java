package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.Objects;

public class BikeType {
	
	private String name;
	private BigDecimal replacementValue;

	public BikeType(String name, BigDecimal replacementValue) {
		this.name = name;
		this.replacementValue = replacementValue;
	}
	
    public BigDecimal getReplacementValue() {
        return replacementValue;
    }
}