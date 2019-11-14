package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SystemTests {
    ContactDetails prov1Details = new ContactDetails(new Location("EH1 1BR", "123 Test Street"), 123);
    ContactDetails prov2Details = new ContactDetails(new Location("G21 1BS", "456 Test Street"), 456);
    ContactDetails prov3Details = new ContactDetails(new Location("EH1 1BT", "789 Test Street"), 789);
    BikeProvider prov1 = new BikeProvider("Awesome Provider", prov1Details, new HashMap<String, String>());
    BikeProvider prov2 = new BikeProvider("Distant Provider", prov2Details, new HashMap<String, String>());
    BikeProvider prov3 = new BikeProvider("Bad Provider", prov3Details, new HashMap<String, String>());
	BikeType Street = new BikeType("Street", new BigDecimal(100));
	BikeType BMX = new BikeType("BMX", new BigDecimal(150));
	QuoteController controller = new QuoteController();
    
    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        
        // Put your test setup here
    }
    
    // TODO: Write system tests covering the three main use cases
    
    @Test
    void testGetQuotes() {
    	prov1.addBiketoStore(new Bike(Street, LocalDate.of(2011, 5, 5)));
    	prov1.addBiketoStore(new Bike(BMX, LocalDate.of(2010, 12, 5)));
    	prov2.addBiketoStore(new Bike(Street, LocalDate.of(2011, 6, 12)));
    	prov2.addBiketoStore(new Bike(BMX, LocalDate.of(2010, 12, 12)));
    	prov3.addBiketoStore(new Bike(Street, LocalDate.of(2009, 5, 5)));
    	DateRange dates = new DateRange(LocalDate.of(2005, 1, 1), LocalDate.of(2005, 1, 5));
    	ArrayList<BikeProvider> providers = new ArrayList<BikeProvider>();
    	providers.add(prov1);
    	providers.add(prov2);
    	providers.add(prov3); // Populate list with all providers in Scotland
    	Map<BikeType, Integer> bikes = new HashMap<>();
    	prov1.setTypePrice(BMX, new BigDecimal(50));
    	prov1.setTypePrice(Street, new BigDecimal(60));
    	prov2.setTypePrice(BMX, new BigDecimal(50));
    	prov2.setTypePrice(Street, new BigDecimal(60));
    	prov3.setTypePrice(Street, new BigDecimal(50));
    	bikes.put(BMX, 1);
    	bikes.put(Street, 1);
    	controller.getQuotes(dates, providers, bikes, new Location("EH3 6ST", "A totally real address"));
    	
    	for (Quote quote:controller.getQuoteList()) {
    	    System.out.println(quote.getBookingRange().toString());
    	    for (Bike bike: quote.getBikeList()) {
    	        System.out.println(bike.toString());
    	    }
    	    System.out.println(quote.getProvider().getStoreName());
    	}
    	
    	assertEquals(controller.getQuoteList().size(),1);
    }
    
}
