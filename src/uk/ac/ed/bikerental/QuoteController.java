package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class QuoteController {
	
	public ArrayList<Quote> quoteList;

	public QuoteController() {
		quoteList = new ArrayList<Quote>();
	}

	public Collection<Quote> getQuotes(DateRange dates, ArrayList<BikeProvider> providers, Map<BikeType, Integer> bikes, Location location) {
		Collection<BikeProvider> nearbyProviders = getNearbyProviders(providers, location);
		for(BikeProvider provider:nearbyProviders) {
			Quote result = getQuoteForProvider(dates, provider, bikes);
			if(result != null) {
				quoteList.add(result);
			}
		}
		return quoteList;
	}

	private ArrayList<BikeProvider> getNearbyProviders(ArrayList<BikeProvider> providers, Location location) {
		ArrayList<BikeProvider> nearbyProviders = new ArrayList<BikeProvider>();
		for(BikeProvider provider:providers) {
			if(provider.getContactDetails().getLocation().isNearTo(location)) {
				nearbyProviders.add(provider);
			}
		}
		return nearbyProviders;
	}

	private Quote getQuoteForProvider(DateRange dates, BikeProvider provider, Map<BikeType, Integer> bikes) {
		ArrayList<Bike> bikeList = new ArrayList<Bike>(); // Initialise bikeList
		for(Map.Entry<BikeType,Integer> entry:bikes.entrySet()) { // For each bike in the desired order
			ArrayList<Bike> available = provider.getAvailableForType(entry.getKey(), dates); // Check how many of that type are available from the provider
			if(available.size() >= entry.getValue()) { // If there are enough available bikes, get them, else return null
				int i = 0; 
				for(Bike bike:available) { // Get the first n bikes from the provider (where n is the desired number)
					if(i < entry.getValue()) {
						bikeList.add(bike);
						i++;
					}
				}
			} else {
				return null;
			}
		}
		Quote quote = new Quote(dates, provider, bikeList);
		return quote;
	}

	public Booking bookQuote(Quote chosenQuote) {
		return null;
		// TODO Auto-generated method stub
		
	}

}
