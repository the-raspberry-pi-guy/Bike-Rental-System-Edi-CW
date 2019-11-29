package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class QuoteController {
    private Customer customerOwner;

	@Override
    public String toString() {
        return "QuoteController [customerOwner=" + customerOwner + "]";
    }

    public QuoteController(Customer owner) {
		this.customerOwner = owner;
	}

	public Set<Quote> getQuotes(DateRange dates, HashSet<BikeProvider> allBikeProviders, Map<BikeType, Integer> bikes, Location location) {
		HashSet<Quote> resultListQuotes = new HashSet<Quote>();
		//First, narrow by location, we only want nearby providers
		Collection<BikeProvider> nearbyProviders = getNearbyProviders(allBikeProviders, location);
		for(BikeProvider provider:nearbyProviders) {
			//Attempt to build a Quote object using getQuoteForProvider
			Quote result = getQuoteForProvider(dates, provider, bikes);
			//If a Quote could be made, add it to the Set of Quotes to return
			if(result != null) {
				resultListQuotes.add(result);
			}
		}
		return resultListQuotes;
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
		// Builds up the bikeList if possible
		addIfAvailable(provider, desiredBikeMap, dates, bikeList);
		// If we didn't get enough bikes the list should be empty
		if(bikeList.size() == 0) return null;
		//Get the total price and deposit for the quote
		BigDecimal totalPrice = getTotalPrice(desiredBikeMap, provider, dates);
		if(totalPrice == null) return null;
		BigDecimal totalDeposit = getDeposit(totalPrice, provider);
		//Finally, we can build the Quote object and return it
		Quote quote = new Quote(dates, provider, bikeList, totalPrice, totalDeposit);
		return quote;
	}
	
	private void addIfAvailable(BikeProvider provider, Map<BikeType, Integer> desiredBikeMap, DateRange dates, Set<Bike> bikeList) {
		for(Map.Entry<BikeType,Integer> chosenType:desiredBikeMap.entrySet()) { // For each bike in the desired order
			Set<Bike> available = provider.getAvailableForType(chosenType.getKey(), dates); // Check how many of that type are available from the provider
			// If the number of available bikes for the given provider is enough to support the quote, add those bikes to the bikeList
			if(available.size() >= chosenType.getValue()) {
				int i = 0; 
				for(Bike bike:available) {
					if(i < chosenType.getValue()) {
						bikeList.add(bike);
						i++;
					}
				}
			} else {
				bikeList.clear();
			}
		}
	}

	private BigDecimal getTotalPrice(Map<BikeType, Integer> desiredBikeMap, BikeProvider provider, DateRange dates) {
		BigDecimal totalPrice = new BigDecimal("0");
		//For each of our desired bikes, get its daily price if possible and return the basic price calculation (value * days)
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
		// Create a booking object with the given parameters and add it to the provider's bookingList in BookingController
		Booking booking = new Booking(chosenQuote.getBookingRange(), requiresDelivery, chosenQuote.getTotalPrice(), chosenQuote.getTotalDeposit(), 
		        chosenQuote.getBikeList(), chosenQuote.getProvider(), customer);
		chosenQuote.getProvider().getBookingList().add(booking);
		//Make each bike in the booking unavailable over the hire dates using makeUnavailable
		for (Bike bike:booking.getBikeList()) {
		    bike.makeUnavailable(booking.getHireDates());
		}
		//If delivery is needed, schedule it using the DeliveryService
		if (requiresDelivery) {
	          DeliveryServiceFactory.getDeliveryService().scheduleDelivery(booking, booking.getHireProvider().getContactDetails().getLocation(), 
	                  booking.getCustomer().getCustomerDetails().getLocation(), booking.getHireDates().getStart());
		}
		
		return booking;
	}
	

    @Override
    public int hashCode() {
        return Objects.hash(customerOwner);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QuoteController other = (QuoteController) obj;
        return Objects.equals(customerOwner, other.customerOwner);
    }
}
