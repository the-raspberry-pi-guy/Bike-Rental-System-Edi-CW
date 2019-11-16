package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class QuoteController {
	
	private ArrayList<Quote> quoteList;

	public QuoteController() {
		quoteList = new ArrayList<Quote>();
	}

	public Collection<Quote> getQuotes(DateRange dates, ArrayList<BikeProvider> allBikeProviders, Map<BikeType, Integer> bikes, Location location) {
		quoteList.clear();
		Collection<BikeProvider> nearbyProviders = getNearbyProviders(allBikeProviders, location);
		for(BikeProvider provider:nearbyProviders) {
			Quote result = getQuoteForProvider(dates, provider, bikes);
			if(result != null) {
				quoteList.add(result);
			}
		}
		return quoteList;
	}

	// Takes in a large list of all providers in Scotland, and returns only providers that are near location
	private ArrayList<BikeProvider> getNearbyProviders(ArrayList<BikeProvider> allBikeProviders, Location location) {
		ArrayList<BikeProvider> nearbyProviders = new ArrayList<BikeProvider>();
		for(BikeProvider provider:allBikeProviders) {
			if(provider.getContactDetails().getLocation().isNearTo(location)) {
				nearbyProviders.add(provider);
			}
		}
		return nearbyProviders;
	}

	private Quote getQuoteForProvider(DateRange dates, BikeProvider provider, Map<BikeType, Integer> desiredBikeMap) {
		ArrayList<Bike> bikeList = new ArrayList<Bike>(); // Initialise bikeList
		for(Map.Entry<BikeType,Integer> chosenType:desiredBikeMap.entrySet()) { // For each bike in the desired order
			ArrayList<Bike> available = provider.getAvailableForType(chosenType.getKey(), dates); // Check how many of that type are available from the provider
			if(available.size() >= chosenType.getValue()) { // If there are enough available bikes, get them, else return null
				int i = 0; 
				for(Bike bike:available) { // Get the first n bikes from the provider (where n is the desired number)
					if(i < chosenType.getValue()) {
						bikeList.add(bike);
						i++;
					}
				}
			} else {
				return null;
			}
		}
		BigDecimal totalPrice = getTotalPrice(desiredBikeMap, provider, dates);
		if(totalPrice == null) {
			return null;
		}
		Quote quote = new Quote(dates, provider, bikeList, totalPrice);
		return quote;
	}

	private BigDecimal getTotalPrice(Map<BikeType, Integer> desiredBikeMap, BikeProvider provider, DateRange dates) {
		BigDecimal totalPrice = new BigDecimal(0);
		for(Map.Entry<BikeType,Integer> currentType:desiredBikeMap.entrySet()){
			BigDecimal dailyPrice = provider.getDailyPrice(currentType.getKey());
			if(dailyPrice == null) { // Meant to return null if a daily price has not been set
				System.out.println(String.format("No daily price for bikeType %s from provider %s", currentType.getKey().getBikeType(),provider.getStoreName()));
				return null;
			}
			BigDecimal typePrice = dailyPrice.multiply(new BigDecimal(currentType.getValue())); // Multiply by the number of desired bikes of that type
			typePrice = typePrice.multiply(new BigDecimal(ChronoUnit.DAYS.between(dates.getStart(),dates.getEnd())+1)); // Multiplies by the number of days
			totalPrice = totalPrice.add(typePrice);
		}
		return totalPrice;
	}

	public Booking bookQuote(Quote chosenQuote) {
		return null;
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Quote> getQuoteList(){
	    return quoteList;
	}

}
