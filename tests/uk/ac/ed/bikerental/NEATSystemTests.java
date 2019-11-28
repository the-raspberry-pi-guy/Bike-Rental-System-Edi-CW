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
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class NEATSystemTests {
    // You can add attributes here
    private Customer testCustomer;
    private BikeProvider ediProvider1;
    private BikeProvider ediProvider2;
    private BikeProvider ediProvider3;
    private BikeProvider glasgowProvider1;
    private BikeType street;
    private BikeType bmx;
    private BikeType mountain;
    private QuoteController quoteController;
    private HashSet<BikeProvider> scottishBikeProviders;

    @BeforeAll
    static void startTests() {
        System.out.println("Starting overall system tests");
    }
    
    @BeforeEach
    void setUp() throws Exception {
        // Setup mock delivery service before each tests
        DeliveryServiceFactory.setupMockDeliveryService();
        
        // Setup for use-case 1: Finding a quote
        
        // New customer, with name, location and number. Wishes for details to be deleted after booking (true delete flag)
        this.testCustomer = new Customer("Matt", "Timmons-Brown", 
                new ContactDetails(new Location("EH4 9EF", "Edinburgh University, Crichton Street"), "07975558134"),
                true);
        
        // Populate some test providers with name, contact information and an (empty) hashmap representing opening hours
        this.ediProvider1 = new BikeProvider("Edinburgh Provider 1",
                new ContactDetails(new Location("EH1 1BR", "123 Test Street"), "01341675432"), new HashMap<String, String>());
        this.ediProvider2 = new BikeProvider("Edinburgh Provider 2",
                new ContactDetails(new Location("EH3 1BT", "456 Test Street"), "01341976445"), new HashMap<String, String>());
        this.ediProvider3 = new BikeProvider("Edinburgh Provider 3",
                new ContactDetails(new Location("EH9 8GH", "789 Test Street"), "01341912910"), new HashMap<String, String>());
        this.glasgowProvider1 = new BikeProvider("Glasgow Provider 1",
                new ContactDetails(new Location("G2 7EX", "321 Bad Street"), "03434167543"), new HashMap<String, String>());
    
        // Create some example bike types with replacement values
        // EdiProvider1 is the instigator of these creations
        this.street = ediProvider1.createBikeType("Street", new BigDecimal("2000"));
        this.bmx = ediProvider1.createBikeType("BMX", new BigDecimal("400"));
        this.mountain = ediProvider1.createBikeType("Mountain", new BigDecimal("700"));
        
        // Populate each store with some bikes
        // ediProvider1 has stock of: 1 street bike, 3 mountain bikes, 0 BMX bikes
        ediProvider1.addBiketoStore(new Bike(street, LocalDate.of(2012, 5, 21)));
        ediProvider1.addBiketoStore(new Bike(mountain, LocalDate.of(2015, 4, 21)));
        ediProvider1.addBiketoStore(new Bike(mountain, LocalDate.of(2015, 3, 21)));
        ediProvider1.addBiketoStore(new Bike(mountain, LocalDate.of(2015, 2, 21)));
        // Set ediProvider1 daily rental prices
        ediProvider1.setTypePrice(street, new BigDecimal("50"));
        ediProvider1.setTypePrice(mountain, new BigDecimal("40"));
        // Set ediProvider1 deposit rate
        ediProvider1.setDepositRate(new BigDecimal("5"));
        
        // ediProvider2 has stock of: 3 street bikes, 0 mountain bikes, 2 BMX bikes
        ediProvider2.addBiketoStore(new Bike(street, LocalDate.of(2017, 5, 21)));
        ediProvider2.addBiketoStore(new Bike(street, LocalDate.of(2017, 4, 21)));
        ediProvider2.addBiketoStore(new Bike(street, LocalDate.of(2017, 3, 21)));
        ediProvider2.addBiketoStore(new Bike(bmx, LocalDate.of(2018, 2, 21)));
        ediProvider2.addBiketoStore(new Bike(bmx, LocalDate.of(2018, 2, 21)));
        // Set ediProvider2 daily rental prices
        ediProvider2.setTypePrice(street, new BigDecimal("70"));
        ediProvider2.setTypePrice(bmx, new BigDecimal("20"));
        // Set ediProvider2 deposit rate
        ediProvider2.setDepositRate(new BigDecimal("10"));

        // ediProvider3 has stock of: 2 street bikes, 1 mountain bike, 1 BMX bike
        ediProvider3.addBiketoStore(new Bike(street, LocalDate.of(2018, 5, 21)));
        ediProvider3.addBiketoStore(new Bike(street, LocalDate.of(2018, 4, 21)));
        ediProvider3.addBiketoStore(new Bike(mountain, LocalDate.of(2014, 3, 21)));
        ediProvider3.addBiketoStore(new Bike(bmx, LocalDate.of(2019, 2, 21)));
        // Set ediProvider3 daily rental prices
        ediProvider3.setTypePrice(street, new BigDecimal("45"));
        ediProvider3.setTypePrice(mountain, new BigDecimal("30"));
        ediProvider3.setTypePrice(bmx, new BigDecimal("10"));
        // Set ediProvider3 deposit rate
        ediProvider3.setDepositRate(new BigDecimal("30"));

        // glasgowProvider1 has stock of: 2 street bikes, 0 mountain bikes, 1 BMX bikes
        glasgowProvider1.addBiketoStore(new Bike(street, LocalDate.of(2015, 5, 21)));
        glasgowProvider1.addBiketoStore(new Bike(street, LocalDate.of(2015, 4, 21)));
        glasgowProvider1.addBiketoStore(new Bike(bmx, LocalDate.of(2009, 2, 21)));
        // Set glasgowProvider1 daily rental prices
        glasgowProvider1.setTypePrice(street, new BigDecimal("30"));
        glasgowProvider1.setTypePrice(bmx, new BigDecimal("8"));
        // Set glasgowProvider1 deposit rate
        glasgowProvider1.setDepositRate(new BigDecimal("40"));
        
        // Setup Quote Controller
        this.quoteController = new QuoteController(testCustomer);
        
        // Add all of the providers to a list of providers in Scotland
        this.scottishBikeProviders =
                new HashSet<BikeProvider>
        (Arrays.asList(ediProvider1, ediProvider2, ediProvider3, glasgowProvider1));
    
    }
    
    /// TEST 1 & TEST 2 DEMONSTRATE THE FUNCTIONALITY OF THE SYSTEM IN USE-CASE 1
    
    @Test
    @DisplayName("TEST 1: Test a bike query with all bikes available. EXCLUDES "
            + "non-nearby providers")
    // Tests whether right stores will provide quotes (based on location and number of bikes in store)
    // Will exclude Glasgow store as it is not nearby
    void findingQuoteNoUnavailTest() {
        // Setup the query and add the bikes and desired quantities
        Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 1 BMX bike, 2 street bikes
        desiredBikes.put(bmx, 1);
        desiredBikes.put(street, 2);
        
        // Get quotes in Edinburgh between 10th Nov and 20th Nov 2019, in EH postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2019, 10, 10), LocalDate.of(2019, 10, 20));     
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, 
                desiredBikes, new Location("EH3 6ST", "Carl Sagan Avenue, Edinburgh"));
        
        HashMap<BikeType, Integer> resultBikes = new HashMap<BikeType, Integer>();
        
        // Loops through the returned bikes in each quote, counts how many there are of each type
        // This is then checked against the original query, and equality is asserted
        for (Quote quote:result) {
            if ((quote.getProvider() != ediProvider2) && (quote.getProvider() != ediProvider3)) {
                assertTrue(false);
            }
            else {
                resultBikes.clear();
                HashSet<Bike> bikesInQuote = quote.getBikeList();
                for (Bike bike:bikesInQuote) {
                    if(resultBikes.containsKey(bike.getType())) {
                        resultBikes.replace(bike.getType(), resultBikes.get(bike.getType()) + 1);
                    } else {
                        resultBikes.put(bike.getType(), 1);
                    }
                }
                assertEquals(desiredBikes, resultBikes);
            } 
        }
    }
    
    @Test
    @DisplayName("TEST 2: Test a bike query where some bikes are already booked. EXCLUDES non-nearby providers "
            + "AND bikes that are already booked")
    // Tests whether right stores will provide quotes (based on location, number of bikes in store and whether bikes booked or not)
    // Creates a previous booking in the system and then test that bikes at that store are excluded within that date range.
    void findingQuoteWUnavailTest() {
        // Make bikes in EdiProvider3 unavailable by creating a previous booking
        DateRange prevBookingDates = new DateRange(LocalDate.of(2019, 12, 1), LocalDate.of(2019,12,8));
        HashMap <BikeType, Integer> bikesToMakeUnavail = new HashMap<>(); // Remove both bikes from EdiProvider 3
        bikesToMakeUnavail.put(street, 2);
        Set<Quote> prevResult = quoteController.getQuotes(prevBookingDates, scottishBikeProviders, 
                bikesToMakeUnavail, new Location("EH3 6ST", "Carl Sagan Avenue, Edinburgh"));
        for (Quote quote:prevResult) {
            if (quote.getProvider() == ediProvider3) { // Book the bikes for ediProvider3
                quoteController.bookQuote(quote, testCustomer, false);
            }
        }
        
        // Setup the query and add the bikes and desired quantities
        Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 1 BMX bike, 2 street bikes
        desiredBikes.put(bmx, 1);
        desiredBikes.put(street, 2);
        
        // Get quotes in Edinburgh between 5th Dec and 7th Dec 2019, in EH postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2019, 12, 5), LocalDate.of(2019, 12, 7));     
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, 
                desiredBikes, new Location("EH3 6ST", "Carl Sagan Avenue, Edinburgh"));
        
        HashMap<BikeType, Integer> resultBikes = new HashMap<BikeType, Integer>();
                
        // Loops through the returned bikes in each quote, counts how many there are of each type
        // This is then checked against the original query, and equality is asserted
        // Should only return EdiProvider2
        for (Quote quote:result) {
            if (quote.getProvider() != ediProvider2) {
                assertTrue(false);
            }
            else {
                resultBikes.clear();
                HashSet<Bike> bikesInQuote = quote.getBikeList();
                for (Bike bike:bikesInQuote) {
                    if(resultBikes.containsKey(bike.getType())) {
                        resultBikes.replace(bike.getType(), resultBikes.get(bike.getType()) + 1);
                    } else {
                        resultBikes.put(bike.getType(), 1);
                    }
                }
                assertEquals(desiredBikes, resultBikes);
            } 
        }
    }
    
    /// TEST 3 IS FOR USE-CASE 2
    
    @Test
    @DisplayName("TEST 3: Creates a booking for a quote picked from an arbitrary returned store & validates booking against quote")
    // Gathers quotes and then creates a unique booking for a customer's desired store
    // Test checks that the returned booking is the same as the quote selected
    void bookQuotefromStoreTest() {
        // Setup the query and add the bikes and desired quantities
        Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 1 MTB bike, 1 street bike
        desiredBikes.put(mountain, 1);
        desiredBikes.put(street, 1);
        
        // Get quotes in Edinburgh between 5th May 2020 and 15th May 2020, in EH postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2020, 5, 5), LocalDate.of(2020, 5, 15));     
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, 
                desiredBikes, new Location("EH3 XEY", "Gus Grissom Square, Edinburgh"));
        
        Booking newBooking = null;
        Quote selectedQuote = null;
        // Choose ediProvider3 to fulfill the order, and set to not requiring delivery
        for (Quote quote:result) {
            if (quote.getProvider() == ediProvider3) {
                selectedQuote = quote;
                newBooking = quoteController.bookQuote(quote, testCustomer, false);
            }
        }
        
        assertTrue((newBooking.getHireDates() == desiredDates) && (newBooking.isRequiresDelivery() == false)
                && (newBooking.getCustomer() == testCustomer) && (newBooking.getHireProvider() == ediProvider3)
                && (newBooking.getTotalPrice() == selectedQuote.getTotalPrice()) 
                && (newBooking.getDepositAmount() == selectedQuote.getTotalDeposit())
                && (newBooking.getBikeList() == selectedQuote.getBikeList()));
    }
}
