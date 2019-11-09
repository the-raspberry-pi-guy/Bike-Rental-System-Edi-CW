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
    // You can add attributes here

    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        
        // Put your test setup here
        BikeProvider prov1 = new BikeProvider();
        BikeProvider prov2 = new BikeProvider();
        BikeProvider prov3 = new BikeProvider();
        QuoteController controller = new QuoteController();
    }
    
    // TODO: Write system tests covering the three main use cases

    @Test
    void myFirstTest() {
        // JUnit tests look like this
        assertEquals("The moon", "cheese"); // Should fail
    }
    
    // Test the happy scenario - for a correct input, nearby correct quotes should be shown.
    // Query should return quotes from the two nearby stores (prov1 and prov2) but not prov3.
    @Test
    void returnQuotesForCorrectInput() {
    	Collection<Quote> quotes = controller.getQuotes();
    	assetEquals(quotes.length, 2);
    }
}
