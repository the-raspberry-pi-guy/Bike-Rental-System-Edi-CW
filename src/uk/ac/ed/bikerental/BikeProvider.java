package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BikeProvider {
	
	private String storeName;
	private ContactDetails providerDetails;
	private Map<String, String> openHours; // Map from day to hour range
	private ArrayList<BikeProvider> partners;
	private Map<BikeType, BigDecimal> typePrice; // Map from Bike Type to BigDecimal price
	private ArrayList<Bike> providerBikes;
	private BigDecimal depositRate;
	private BookingController bookingController;
	
	public BikeProvider(String name, ContactDetails providerDetails, Map<String, String> openHours) {
		this.storeName = name;
		this.providerDetails = providerDetails;
		this.openHours = openHours;
		
		this.partners = new ArrayList<BikeProvider>();
	    this.providerBikes = new ArrayList<Bike>();
	    this.typePrice = new HashMap<BikeType, BigDecimal>();
	    this.depositRate = new BigDecimal("0");
	    bookingController = new BookingController(this);
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
	    			bike.onDropoff();
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((depositRate == null) ? 0 : depositRate.hashCode());
        result = prime * result + ((openHours == null) ? 0 : openHours.hashCode());
        result = prime * result + ((partners == null) ? 0 : partners.hashCode());
        result = prime * result + ((providerBikes == null) ? 0 : providerBikes.hashCode());
        result = prime * result + ((providerDetails == null) ? 0 : providerDetails.hashCode());
        result = prime * result + ((storeName == null) ? 0 : storeName.hashCode());
        result = prime * result + ((typePrice == null) ? 0 : typePrice.hashCode());
        return result;
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
        if (depositRate == null) {
            if (other.depositRate != null)
                return false;
        } else if (!depositRate.equals(other.depositRate))
            return false;
        if (openHours == null) {
            if (other.openHours != null)
                return false;
        } else if (!openHours.equals(other.openHours))
            return false;
        if (partners == null) {
            if (other.partners != null)
                return false;
        } else if (!partners.equals(other.partners))
            return false;
        if (providerBikes == null) {
            if (other.providerBikes != null)
                return false;
        } else if (!providerBikes.equals(other.providerBikes))
            return false;
        if (providerDetails == null) {
            if (other.providerDetails != null)
                return false;
        } else if (!providerDetails.equals(other.providerDetails))
            return false;
        if (storeName == null) {
            if (other.storeName != null)
                return false;
        } else if (!storeName.equals(other.storeName))
            return false;
        if (typePrice == null) {
            if (other.typePrice != null)
                return false;
        } else if (!typePrice.equals(other.typePrice))
            return false;
        return true;
    }


	public HashSet<Booking> getBookingList() {
		return bookingController.getBookingList();
	}
}
