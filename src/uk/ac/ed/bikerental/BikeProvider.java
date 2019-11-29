package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class BikeProvider {
	
	private String storeName;
	private ContactDetails providerDetails;
	private Map<String, String> openHours; // Map from day to hour range
	private HashSet<BikeProvider> partners;
	private Map<BikeType, BigDecimal> typePrice; // Map from Bike Type to BigDecimal price
	private HashSet<Bike> providerBikes;
	private BigDecimal depositRate;
	private BookingController bookingController;
	
	public BikeProvider(String name, ContactDetails providerDetails, Map<String, String> openHours) {
		this.storeName = name;
		this.providerDetails = providerDetails;
		this.openHours = openHours;
		
		this.partners = new HashSet<BikeProvider>();
	    this.providerBikes = new HashSet<Bike>();
	    this.typePrice = new HashMap<BikeType, BigDecimal>();
	    this.depositRate = new BigDecimal("0");
	    this.bookingController = new BookingController();
	}
	
	
	@Override
    public String toString() {
        return "BikeProvider [storeName=" + storeName + ", providerDetails=" + providerDetails + "]";
    }


    public void addBiketoStore(Bike bike) {
	    providerBikes.add(bike);
	}
	
	public BikeType createBikeType(String name, BigDecimal replacementValue) {
	    return new BikeType(name, replacementValue);
	}
	
	public void setTypePrice(BikeType type, BigDecimal dailyRentalPrice) {
		if(typePrice.containsKey(type)) {
			typePrice.replace(type, dailyRentalPrice);
		} else {
		    typePrice.put(type, dailyRentalPrice);
		}
	}
	
	public BigDecimal getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(BigDecimal rate) {
	    this.depositRate = rate;
	}
	
	public void addPartner(BikeProvider otherProvider) {
	    partners.add(otherProvider);
	}
	
	public void removePartner(BikeProvider otherProvider) {
	    partners.remove(otherProvider);
	}
	
	public void recordBikeReturn(UUID orderNo) {
	    for(Booking booking: bookingController.getBookingList()) {
	    	if(booking.getOrderNo().equals(orderNo)) {
	    		for(Bike bike: booking.getBikeList()) {
	    			//bike.onDropoff();
	    			if(booking.getHireProvider() != this) {
	    				notifyOriginalProvider(orderNo);
	    			}
	    		}
	    	}
	    }
	} 
	
	public void notifyOriginalProvider(UUID orderNo) {
	    System.out.println("The bikes from order " + orderNo.toString() + " have been returned to a partner store.");
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

    public HashSet<BikeProvider> getPartners() {
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

    public HashSet<Bike> getProviderBikes() {
        return providerBikes;
    }

    public Set<Bike> getAvailableForType(BikeType type, DateRange dates) {
		Set<Bike> bikes = new HashSet<Bike>();

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

    @Override
    public int hashCode() {
        return Objects.hash(providerDetails, storeName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BikeProvider other = (BikeProvider) obj;
        return Objects.equals(bookingController, other.bookingController)
                && Objects.equals(depositRate, other.depositRate) && Objects.equals(openHours, other.openHours)
                && Objects.equals(partners, other.partners) && Objects.equals(providerBikes, other.providerBikes)
                && Objects.equals(providerDetails, other.providerDetails) && Objects.equals(storeName, other.storeName)
                && Objects.equals(typePrice, other.typePrice);
    }


	public HashSet<Booking> getBookingList() {
		return bookingController.getBookingList();
	}
}
