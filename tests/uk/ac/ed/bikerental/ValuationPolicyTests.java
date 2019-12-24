package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.*;

public class ValuationPolicyTests {
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
        DeliveryService mock = DeliveryServiceFactory.getDeliveryService();
        
        // Setup for all use cases
        
        // New customer, with name, location and number, false delete flag
        this.testCustomer = new Customer("Matt", "Timmons-Brown", 
                new ContactDetails(new Location("EH4 9EF", "Edinburgh University, Crichton Street"), "07975558134"),
                false);
        
        // Populate some test providers with name, contact information and an (empty as not necessary) HashMap representing opening hours
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
        this.scottishBikeProviders = new HashSet<BikeProvider>(Arrays.asList
                (ediProvider1, ediProvider2, ediProvider3, glasgowProvider1));
    
    }
    
    //Tests Linear Depreciation valuation, should correctly return a new deposit based on the
    //depreciated value of the bikes;
    
    @Test
    @DisplayName("TEST 1: Test Linear Depreciation valuation policy")
    void testLinearDepreciation() {
    	Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 1 BMX bike, 2 street bikes
        desiredBikes.put(bmx, 1);
        desiredBikes.put(street, 2);
        
        // Get quotes in Edinburgh between 10th Nov and 20th Nov 2019, in EH postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2019, 10, 10), LocalDate.of(2019, 10, 20));     
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, 
                desiredBikes, new Location("EH3 6ST", "Carl Sagan Avenue, Edinburgh"), null, new LinearDepreciation(new BigDecimal(10)));
        
        for (Quote quote:result) {
            BigDecimal totalPrice = quote.getTotalPrice();
            BigDecimal totalDeposit = quote.getTotalDeposit();
            
            if (quote.getProvider() == ediProvider2) {
                assertEquals(new BigDecimal("1600").stripTrailingZeros(), totalPrice.stripTrailingZeros());
                assertEquals(new BigDecimal("356").stripTrailingZeros(), totalDeposit.stripTrailingZeros());
            }
            if (quote.getProvider() == ediProvider3) {
                assertEquals(new BigDecimal("1000").stripTrailingZeros(), totalPrice.stripTrailingZeros());
                assertEquals(new BigDecimal("1200").stripTrailingZeros(), totalDeposit.stripTrailingZeros());
            }
        }
        
    }
    
    //Tests DDBD valuation, should correctly return a new deposit based on the
    //depreciated value of the bikes;
    
    @Test
    @DisplayName("TEST 2: Test DDBD valuation policy")
    void testDDBDepreciation() {
    	Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 1 BMX bike, 2 street bikes
        desiredBikes.put(bmx, 1);
        desiredBikes.put(street, 2);
        
        // Get quotes in Edinburgh between 10th Nov and 20th Nov 2019, in EH postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2019, 10, 10), LocalDate.of(2019, 10, 20));     
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, 
                desiredBikes, new Location("EH3 6ST", "Carl Sagan Avenue, Edinburgh"), null, new DoubleDecliningBalanceDepreciation(new BigDecimal(10)));
        
        for (Quote quote:result) {
            BigDecimal totalPrice = quote.getTotalPrice();
            BigDecimal totalDeposit = quote.getTotalDeposit();
            
            if (quote.getProvider() == ediProvider2) {
                assertEquals(new BigDecimal("1600").stripTrailingZeros(), totalPrice.stripTrailingZeros());
                assertEquals(new BigDecimal("288").stripTrailingZeros(), totalDeposit.stripTrailingZeros());
            }
            if (quote.getProvider() == ediProvider3) {
                assertEquals(new BigDecimal("1000").stripTrailingZeros(), totalPrice.stripTrailingZeros());
                assertEquals(new BigDecimal("1080").stripTrailingZeros(), totalDeposit.stripTrailingZeros());
            }
        }
        
    }
}
