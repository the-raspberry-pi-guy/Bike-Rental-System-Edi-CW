package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class BikeProvider {
	
	private String storeName;
	private ContactDetails providerDetails;
	private Map<String, String> openHours; // Map from day to hour range
	private ArrayList<BikeProvider> partners;
	private Map<BikeType, BigDecimal> typePrice; // Map from Bike Type to BigDecimal price
	private ArrayList<Bike> bikes;
	
	public BikeProvider(String name, ContactDetails providerDetails, Map<String, String> openHours) {
		this.storeName = name;
		this.providerDetails = providerDetails;
		this.openHours = openHours;
		
		partners = new ArrayList<BikeProvider>();
	    bike = new ArrayList<Bike>();
	}
	
	public void addBiketoStore(Bike bike, BigDecimal dailyRentalPrice) {
	    
	}
	
	public void addBikeTypetoStore(BikeType bikeType) {
	    
	}
	
	public void setTypePrice(BikeType type, BigDecimal dailyRentalPrice) {
	    //TODO
	    // Check if the store has a bike type category
	}
	
	public void addPartner(BikeProvider otherProvider) {
	    partners.add(otherProvider);
	}
	
	public void removePartner(BikeProvider otherProvider) {
	    partners.remove(otherProvider);
	}
	
	public void recordBikeReturn(int orderNo) {
	    //TODO
	}
	
	public void notifyOriginalProvider(int orderNo) {
	    //TODO
	}
}
