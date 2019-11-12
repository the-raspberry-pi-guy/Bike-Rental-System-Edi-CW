package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BikeProvider {
	
	private String storeName;
	private ContactDetails providerDetails;
	private Map<String, String> openHours;
	private Collection<BikeProvider> partners;
	private Map<String, Float> typePrice;
	private ArrayList<Bike> providerBikes;

	public BikeProvider(String name, ContactDetails providerDetails) {
		this.storeName = name;
		this.providerDetails = providerDetails;
		this.providerBikes = new ArrayList<Bike>();
	}

	public Collection<Bike> getProviderBikes() {
		return providerBikes;
	}
	
	public void addBikes(BikeType type, int number) {
		for(int i = 0; i<number; i++) {
			Bike bike = new Bike(type);
			providerBikes.add(bike);
		}
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
