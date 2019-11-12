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
    BikeProvider prov1 = new BikeProvider("Awesome Provider", prov1Details);
    BikeProvider prov2 = new BikeProvider("Distant Provider", prov2Details);
    BikeProvider prov3 = new BikeProvider("Bad Provider", prov3Details);
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
    	prov1.addBikes(Street, 4);
    	prov1.addBikes(BMX, 4);
    	prov2.addBikes(Street, 4);
    	prov2.addBikes(BMX, 4);
    	prov3.addBikes(Street, 1);
    	prov3.addBikes(BMX, 1);
    	DateRange dates = new DateRange(LocalDate.of(2005, 1, 1), LocalDate.of(2005, 1, 5));
    	ArrayList<BikeProvider> providers = new ArrayList<BikeProvider>();
    	providers.add(prov1);
    	providers.add(prov2);
    	providers.add(prov3);
    	Map<BikeType, Integer> bikes = new HashMap<>();
    	bikes.put(BMX, 2);
    	bikes.put(Street, 2);
    	controller.getQuotes(dates, providers, bikes, new Location("EH3 6ST", "A totally real address"));
    	assertEquals(controller.quoteList.size(),1);
    }
    
}
