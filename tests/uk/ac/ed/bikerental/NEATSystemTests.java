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
    
    @Test
    @DisplayName("Test a bike query with all bikes available")
    // Tests whether right stores will provide quotes (based on location and number of bikes in store)
    void findingQuoteNoUnavailTest() {
        // Setup the query and add the bikes and desired quantities
        Map<BikeType, Integer> bikes = new HashMap<>();
        // Would like 1 BMX bike, 2 street bikes
        bikes.put(bmx, 1);
        bikes.put(street, 2);
        
        DateRange desiredDates = new DateRange(LocalDate.of(2019, 10, 10), LocalDate.of(2019, 10, 20));

        // Expected result
        HashSet<Quote> expected = new HashSet<Quote>();
        HashSet<Bike> returnedExpectedBikes = new HashSet<Bike>(Arrays.asList(
                new Bike(street, null), new Bike(street, null), new Bike(bmx, null)));
        
        expected.add(new Quote(desiredDates, ediProvider2, returnedExpectedBikes, new BigDecimal("1600"), new BigDecimal("160")));
        expected.add(new Quote(desiredDates, ediProvider3, returnedExpectedBikes, new BigDecimal("1000"), new BigDecimal("300")));
        
        // Get quotes in Edinburgh between 10th Nov and 20th Nov 2019, in EH postcodes
        
        System.out.println("1: " + ediProvider1.getProviderBikes().toString() + "2: " + ediProvider2.getProviderBikes().toString() + "3: " + ediProvider3.getProviderBikes().toString());
        
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, 
                bikes, new Location("EH3 6ST", "Carl Sagan Avenue, Edinburgh"));
        
        HashMap<BikeType, Integer> resultBikes = new HashMap<BikeType, Integer>();
        
        for (Quote quote:result) {
            resultBikes.clear();
            HashSet<Bike> bikesInQuote = quote.getBikeList();
            for (Bike bike:bikesInQuote) {
                if(resultBikes.containsKey(bike.getType())) {
                    resultBikes.replace(bike.getType(), resultBikes.get(bike.getType()) + 1);
                } else {
                    resultBikes.put(bike.getType(), 1);
                }
            }
            assertEquals(bikes, resultBikes);
        }
    }
}
