package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Bike {
	
	private HashSet<DateRange> datesBooked;
	private BikeType type;
	private LocalDate dateOfPurchase;
	private UUID bikeId;
	
	enum Status {
		IN_TRANSIT_TO_CUSTOMER,
		IN_TRANSIT_TO_STORE,
		WITH_CUSTOMER,
		IN_STORE
	}
	
	private Status bikeStatus;

	public Bike(BikeType type, LocalDate date) {
		this.type = type;
		this.dateOfPurchase = date;
		this.datesBooked = new HashSet<DateRange>();
		this.bikeStatus = Status.IN_STORE;
		this.bikeId = UUID.randomUUID();
	}
	
    public BikeType getType() {
        return type;
    }
    
    public LocalDate getDateOfPurchase() {
    	return dateOfPurchase;
    }
    
    public HashSet<DateRange> getDatesBooked() {
        return datesBooked;
    }

    public UUID getBikeId() {
        return bikeId;
    }

    public void setBikeStatus(Status bikeStatus) {
        this.bikeStatus = bikeStatus;
    }

    public Status getBikeStatus() {
        return bikeStatus;
    }

    @Override
    public String toString() {
        return "Bike [datesBooked=" + datesBooked + ", type=" + type + ", dateOfPurchase=" + dateOfPurchase + "]";
    }

    public Boolean isAvailable(DateRange other) {
        // Tests if any of the dates in a range clash when the bike is not available
        
        for (DateRange range: datesBooked) {
    		if(range.overlaps(other)) {
    			return false;
    		}
    	}
    	return true;
    } 
    
    public void makeUnavailable(DateRange range) {
    	datesBooked.add(range);
    }
    
    public boolean compareType(Bike other) {
        if (this.type == other.getType()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(bikeId, bikeStatus, dateOfPurchase, datesBooked, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bike other = (Bike) obj;
        return Objects.equals(bikeId, other.bikeId) && bikeStatus == other.bikeStatus
                && Objects.equals(dateOfPurchase, other.dateOfPurchase)
                && Objects.equals(datesBooked, other.datesBooked) && Objects.equals(type, other.type);
    }
    
}