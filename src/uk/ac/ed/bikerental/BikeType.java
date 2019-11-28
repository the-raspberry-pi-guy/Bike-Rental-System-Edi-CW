package uk.ac.ed.bikerental;

import java.math.BigDecimal;
// Add id?
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((replacementValue == null) ? 0 : replacementValue.hashCode());
        result = prime * result + ((typeModel == null) ? 0 : typeModel.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BikeType other = (BikeType) obj;
        if (replacementValue == null) {
            if (other.replacementValue != null)
                return false;
        } else if (!replacementValue.equals(other.replacementValue))
            return false;
        if (typeModel == null) {
            if (other.typeModel != null)
                return false;
        } else if (!typeModel.equals(other.typeModel))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BikeType [typeModel=" + typeModel + ", replacementValue=" + replacementValue + "]";
    }
    
}