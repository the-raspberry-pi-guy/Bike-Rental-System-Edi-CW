package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/*
 * The main controller class for handling quotes for a given customer. It allows for core functionality where quotes
 * can be generated and then booked for a given customer that 'owns' the QuoteController. It performs all the key
 * calculations related to this that aren't covered in other objects.
 */

public class QuoteController {
    private Customer customerOwner;

    @Override
    public String toString() {
        return "QuoteController [customerOwner=" + customerOwner + "]";
    }

    public QuoteController(Customer owner) {
        this.customerOwner = owner;
    }

    // Returns a set of matching quotes for a query, with a given pricing policy and valuation policy
    public Set<Quote> getQuotes(DateRange dates, HashSet<BikeProvider> allBikeProviders, Map<BikeType, Integer> bikes, Location location, 
            PricingPolicy pricingPolicy, ValuationPolicy valuationPolicy) {
        
        HashSet<Quote> resultListQuotes = new HashSet<Quote>();
        
        // Get the nearby providers
        Collection<BikeProvider> nearbyProviders = getNearbyProviders(allBikeProviders, location);
        
        // For each bike provider in the nearby providers, get the appropriate quotes and then return them
        for(BikeProvider provider:nearbyProviders) {
            Quote result = getQuoteForProvider(dates, provider, bikes, pricingPolicy, valuationPolicy);
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

	private Quote getQuoteForProvider(DateRange dates, BikeProvider provider, Map<BikeType, Integer> desiredBikeMap, PricingPolicy pricingPolicy, ValuationPolicy valuationPolicy) {
		HashSet<Bike> bikeList = new HashSet<Bike>(); // Initialise bikeList
		
		for(Map.Entry<BikeType,Integer> chosenType:desiredBikeMap.entrySet()) { // For each bike in the desired order
			Set<Bike> available = provider.getAvailableForType(chosenType.getKey(), dates);
			// If the number of available bikes for the given provider is enough to support the quote, add those bikes to the bikeList

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
        BigDecimal totalPrice = getTotalPrice(bikeList, provider, dates, valuationPolicy, pricingPolicy);
        if(totalPrice == null) {
            return null;
        }
        BigDecimal totalDeposit = getDeposit(bikeList, provider);
        Quote quote = new Quote(dates, provider, bikeList, totalPrice, totalDeposit);
        return quote;
    }

    private BigDecimal getTotalPrice(Set<Bike> bikes, BikeProvider provider, DateRange dates, ValuationPolicy valuationPolicy, PricingPolicy pricingPolicy) {
        BigDecimal totalPrice = new BigDecimal("0");
        for(Bike bike: bikes){
        	if((valuationPolicy instanceof LinearDepreciation) || (valuationPolicy instanceof DoubleDecliningBalanceDepreciation)) {
        		bike.setFluidValue(valuationPolicy.calculateValue(bike, LocalDate.now()));
                totalPrice.add(bike.getFluidValue()).multiply(new BigDecimal(dates.toDays()));
        	}
        	else {
                BigDecimal dailyPrice = provider.getDailyPrice(bike.getType());
                if(dailyPrice == null) { // Meant to return null if a daily price has not been set
                    System.out.println(String.format("No daily price for bikeType %s from provider %s", bike.getType(),provider.getStoreName()));
                    return null;
                }
                BigDecimal days = new BigDecimal(dates.toDays());
                totalPrice = totalPrice.add(dailyPrice.multiply(days));
            }
        }
        
        return totalPrice;
    }
    
    // Calculates the deposit given a total price and a bike provider
    private BigDecimal getDeposit(Set<Bike> bikes, BikeProvider provider) {
    	BigDecimal totalDeposit = new BigDecimal(0);
    	
    	for(Bike bike: bikes) {
    		BigDecimal replacementValue = bike.getType().getReplacementValue();
    		BigDecimal percent = provider.getDepositRate().divide(new BigDecimal(100));
    		totalDeposit = totalDeposit.add(replacementValue.multiply(percent));
    	}
    	
    	return totalDeposit;
    }

	public Booking bookQuote(Quote chosenQuote, boolean requiresDelivery) {
		
		// Create a booking object with the given parameters and add it to the provider's bookingList in BookingController
		Booking booking = new Booking(chosenQuote.getBookingRange(), requiresDelivery, chosenQuote.getTotalPrice(), chosenQuote.getTotalDeposit(), 
		chosenQuote.getBikeList(), chosenQuote.getProvider(), customerOwner);
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