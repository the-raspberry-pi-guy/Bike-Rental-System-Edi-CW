package uk.ac.ed.bikerental;

import java.util.Collection;
import java.util.Map;

public class BikeProvider {
	
	private String storeName;
	private ContactDetails providerDetails;
	private Map<String, String> openHours;
	private Collection<BikeProvider> partners;
	private Map<String, Float> typePrice;

	public BikeProvider(String name, ContactDetails providerDetails, Map<String, String> openHours) {
		this.storeName = name;
		this.providerDetails = providerDetails;
		this.openHours = openHours;
	}
}
