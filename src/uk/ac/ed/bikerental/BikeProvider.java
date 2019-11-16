package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BikeProvider {
	
	private String storeName;
	private ContactDetails providerDetails;
	private Map<String, String> openHours; // Map from day to hour range
	private ArrayList<BikeProvider> partners;
	private Map<BikeType, BigDecimal> typePrice; // Map from Bike Type to BigDecimal price
	private ArrayList<Bike> providerBikes;
	
	public BikeProvider(String name, ContactDetails providerDetails, Map<String, String> openHours) {
		this.storeName = name;
		this.providerDetails = providerDetails;
		this.openHours = openHours;
		
		partners = new ArrayList<BikeProvider>();
	    providerBikes = new ArrayList<Bike>();
	    typePrice = new HashMap<BikeType, BigDecimal>();
	}
	
	public void addBiketoStore(Bike bike) {
	    providerBikes.add(bike);
	}
	
	public void addBikeTypetoStore(BikeType bikeType) {
	    
	}
	
	public void setTypePrice(BikeType type, BigDecimal dailyRentalPrice) {
		if(typePrice.containsKey(type)) {
			typePrice.replace(type, dailyRentalPrice);
		} else {
		    typePrice.put(type, dailyRentalPrice);
		}
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
	
	public String getStoreName() {
        return storeName;
    }

    public ContactDetails getProviderDetails() {
        return providerDetails;
    }

    public Map<String, String> getOpenHours() {
        return openHours;
    }

    public ArrayList<BikeProvider> getPartners() {
        return partners;
    }

    public Map<BikeType, BigDecimal> getTypePriceMap() {
        return typePrice;
    }
    
    public BigDecimal getDailyPrice(BikeType type) {
    	if(typePrice.containsKey(type)) {
        	return typePrice.get(type);
    	} else {
    		return null;
    	}
    }

    public ArrayList<Bike> getProviderBikes() {
        return providerBikes;
    }

    public ArrayList<Bike> getAvailableForType(BikeType type, DateRange dates) {
		ArrayList<Bike> bikes = new ArrayList<Bike>();
		for(Bike bike: providerBikes) {
			if(bike.getType() == type && bike.isAvailable(dates)) {
				bikes.add(bike);
			}
		}
		return bikes;
	}
	
	public ContactDetails getContactDetails() {
		return this.providerDetails;
	}
}
