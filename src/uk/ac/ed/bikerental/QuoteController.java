package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class QuoteController {
	
	private HashSet<Quote> quoteList;

	@Override
    public String toString() {
        return "QuoteController [quoteList=" + quoteList + "]";
    }

    public QuoteController() {
		quoteList = new HashSet<Quote>();
	}

	public Set<Quote> getQuotes(DateRange dates, HashSet<BikeProvider> allBikeProviders, Map<BikeType, Integer> bikes, Location location) {
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
	private Set<BikeProvider> getNearbyProviders(HashSet<BikeProvider> allBikeProviders, Location location) {
		Set<BikeProvider> nearbyProviders = new HashSet<BikeProvider>();
		for(BikeProvider provider:allBikeProviders) {
			if(provider.getContactDetails().getLocation().isNearTo(location)) {
				nearbyProviders.add(provider);
			}
		}
		return nearbyProviders;
	}

	private Quote getQuoteForProvider(DateRange dates, BikeProvider provider, Map<BikeType, Integer> desiredBikeMap) {
		HashSet<Bike> bikeList = new HashSet<Bike>(); // Initialise bikeList
		for(Map.Entry<BikeType,Integer> chosenType:desiredBikeMap.entrySet()) { // For each bike in the desired order
			Set<Bike> available = provider.getAvailableForType(chosenType.getKey(), dates); // Check how many of that type are available from the provider
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
		BigDecimal totalDeposit = getDeposit(totalPrice, provider);
		Quote quote = new Quote(dates, provider, bikeList, totalPrice, totalDeposit);
		return quote;
	}

	private BigDecimal getTotalPrice(Map<BikeType, Integer> desiredBikeMap, BikeProvider provider, DateRange dates) {
		BigDecimal totalPrice = new BigDecimal("0");
		for(Map.Entry<BikeType,Integer> currentType:desiredBikeMap.entrySet()){
			BigDecimal dailyPrice = provider.getDailyPrice(currentType.getKey());
			if(dailyPrice == null) { // Meant to return null if a daily price has not been set
				System.out.println(String.format("No daily price for bikeType %s from provider %s", currentType.getKey().getBikeType(),provider.getStoreName()));
				return null;
			}
			BigDecimal typePrice = dailyPrice.multiply(new BigDecimal(currentType.getValue())); // Multiply by the number of desired bikes of that type
			typePrice = typePrice.multiply(new BigDecimal(ChronoUnit.DAYS.between(dates.getStart(),dates.getEnd()))); // Multiplies by the number of days
			totalPrice = totalPrice.add(typePrice);
		}
		return totalPrice;
	}
	
	// Calculates the deposit given a total price and a bike provider
	private BigDecimal getDeposit(BigDecimal totalPrice, BikeProvider provider) {
	    BigDecimal priceDepositMultiplier = new BigDecimal("1").subtract(provider.getDepositRate().divide
	                                                (new BigDecimal("100")));
	    return (totalPrice.subtract(totalPrice.multiply(priceDepositMultiplier)).stripTrailingZeros());
	}

	public Booking bookQuote(Quote chosenQuote, Customer customer, boolean requiresDelivery) {
		
		Booking booking = new Booking(chosenQuote.getBookingRange(), requiresDelivery, chosenQuote.getTotalPrice(), chosenQuote.getTotalDeposit(), chosenQuote.getBikeList(), chosenQuote.getProvider(), customer);
		chosenQuote.getProvider().getBookingList().add(booking);
		return booking;
	}
	
	public HashSet<Quote> getQuoteList(){
	    return quoteList;
	}


}
