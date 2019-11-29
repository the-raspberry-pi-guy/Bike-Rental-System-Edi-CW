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
        DeliveryService mock = DeliveryServiceFactory.getDeliveryService();
        
        // Setup for all use cases
        
        // New customer, with name, location and number. Wishes for details to be deleted after booking (true delete flag)
        this.testCustomer = new Customer("Matt", "Timmons-Brown", 
                new ContactDetails(new Location("EH4 9EF", "Edinburgh University, Crichton Street"), "07975558134"),
                true);
        
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
    
    /// TEST 1, TEST 2, TEST 3 & TEST 4 DEMONSTRATE THE FUNCTIONALITY OF THE SYSTEM IN USE-CASE 1
    /// Finding a quote, excluding non-nearby providers and bikes that are unavailable in a given range
    /// Also checking that the price of a given quote is correct as calculated with the default replacement value
    
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
    @DisplayName("TEST 2: Test the prices (default and based on replacement value, no custom valuation) are correct for the TEST 1 bike query "
            + "with all bikes available. EXCLUDES non-nearby providers")
    // Tests the PRICE from the right stores will provide quotes (based on location and number of bikes in store)
    // Will exclude Glasgow store as it is not nearby. Same query as Test 1.
    void findingQuotePriceNoUnavailTest() {
        // Setup the query and add the bikes and desired quantities
        Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 1 BMX bike, 2 street bikes
        desiredBikes.put(bmx, 1);
        desiredBikes.put(street, 2);
        
        // Get quotes in Edinburgh between 10th Nov and 20th Nov 2019, in EH postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2019, 10, 10), LocalDate.of(2019, 10, 20));     
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, 
                desiredBikes, new Location("EH3 6ST", "Carl Sagan Avenue, Edinburgh"));
        
        // For each quote, check that the calculated total price is correct, and also check that the
        // calculated total deposit is correct. This query only contains EdiProvider2 and EdiProvider3 (from the test before)
        
        // These values are calculated from the default deposit rate of each provider
        for (Quote quote:result) {
            BigDecimal totalPrice = quote.getTotalPrice();
            BigDecimal totalDeposit = quote.getTotalDeposit();
            
            if (quote.getProvider() == ediProvider2) {
                assertEquals(new BigDecimal("1600").stripTrailingZeros(), totalPrice.stripTrailingZeros());
                assertEquals(new BigDecimal("440").stripTrailingZeros(), totalDeposit.stripTrailingZeros());
            }
            if (quote.getProvider() == ediProvider3) {
                assertEquals(new BigDecimal("1000").stripTrailingZeros(), totalPrice.stripTrailingZeros());
                assertEquals(new BigDecimal("1320").stripTrailingZeros(), totalDeposit.stripTrailingZeros());
            }
        }
    } 
    
    @Test
    @DisplayName("TEST 3: Test a bike query where some bikes are already booked. EXCLUDES non-nearby providers "
            + "AND bikes that are already booked")
    // Tests whether right stores will provide quotes (based on location, number of bikes in store and whether bikes booked or not)
    // Creates a previous booking in the system and then test that bikes at that store are excluded within that date range.
    void findingQuoteWUnavailTest() {

        // Make bikes in EdiProvider3 unavailable by creating a previous booking between
        // 1st Dec to the 8th Dec
        DateRange prevBookingDates = new DateRange(LocalDate.of(2019, 12, 1), LocalDate.of(2019,12,8));
        HashMap <BikeType, Integer> bikesToMakeUnavail = new HashMap<>(); // Remove both bikes from EdiProvider 3
        bikesToMakeUnavail.put(street, 2);
        Location location = new Location("EH3 6ST", "Carl Sagan Avenue, Edinburgh");
        
        Set<Quote> prevResult = quoteController.getQuotes(prevBookingDates, scottishBikeProviders, 
                bikesToMakeUnavail, location);
        
        for (Quote quote:prevResult) {
            if (quote.getProvider() == ediProvider3) { // Book the bikes for ediProvider3
                quoteController.bookQuote(quote, false);
            }
        }

        // Setup the query and add the bikes and desired quantities
        Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 1 BMX bike, 2 street bikes
        desiredBikes.put(bmx, 1);
        desiredBikes.put(street, 2);
        
        // Get quotes in Edinburgh between 5th Dec and 7th Dec 2019, in EH postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2019, 12, 5), LocalDate.of(2019, 12, 7)); 
        
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, desiredBikes, location);
        
        // Loops through the returned bikes in each quote, counts how many there are of each type
        // This is then checked against the original query, and equality is asserted
        // Should only return EdiProvider2
        HashMap<BikeType, Integer> resultBikes = new HashMap<BikeType, Integer>();
        
        for (Quote quote:result) {
            if (quote.getProvider() != ediProvider2) {
                assertTrue(false);
            }
            else {
                resultBikes.clear();
                HashSet<Bike> bikesInQuote = quote.getBikeList();
                for (Bike bike:bikesInQuote) {
                    if(resultBikes.containsKey(bike.getType())) {
                        resultBikes.replace(bike.getType(), resultBikes.get(bike.getType()) + 1); // Increment this type of bike
                    } else {
                        resultBikes.put(bike.getType(), 1);
                    }
                }
                assertEquals(desiredBikes, resultBikes);
            } 
        }
    } 
    
    @Test
    @DisplayName("TEST 4: Test a bike query with more bikes than any provider could supply. Should return empty set.")
    // Tests whether providers react as expected to a map of bikes that is simply too 
    // large for them to provide
    void findingLargeQuoteTest() {

        // Setup the query and add the bikes and desired quantities
        Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 3 BMX bikes, 8 street bikes
        desiredBikes.put(bmx, 3);
        desiredBikes.put(street, 8);
        
        // Get quotes in Edinburgh between 19th Dec and 25th Dec 2019, in EH postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2019, 12, 19), LocalDate.of(2019, 12, 25)); 
        
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, desiredBikes,
                new Location("EH9 ABC", "Tim Peake Hill, Edinburgh"));
        
        // Check that the result is just an empty set, as no providers can supply the number of bikes asked for
        assertEquals(new HashSet<Quote>(), result);
    } 
    
    /// TEST 4, TEST 5 & TEST 6 ARE FOR DEMONSTRATING THE FUNCTIONALITY IN USE CASE 2
    /// Booking a Quote with/without delivery service and checking that the returned booking object matches
    /// Also testing that the Booking objects are captured in our system-level BookingController class
    
    @Test
    @DisplayName("TEST 5: Creates a booking for a quote picked from an arbitrary returned store "
            + "& validates booking against quote, no delivery")
    // Gathers quotes and then creates a unique booking for a customer's desired store
    // Test checks that the returned booking is the same as the quote selected
    void bookQuoteNoDelivTest() {
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
                newBooking = quoteController.bookQuote(quote, false);
            }
        }
        
        // Checks that the new booking matches the selected quote, and that a UUID order number has been
        // generated and is not null.
        assertTrue((newBooking.getHireDates() == desiredDates) && (newBooking.isRequiresDelivery() == false)
                && (newBooking.getCustomer() == testCustomer) && (newBooking.getHireProvider() == ediProvider3)
                && (newBooking.getTotalPrice() == selectedQuote.getTotalPrice()) 
                && (newBooking.getDepositAmount() == selectedQuote.getTotalDeposit())
                && (newBooking.getBikeList() == selectedQuote.getBikeList())
                && (newBooking.getOrderNo() != null));
    }
    
    @Test
    @DisplayName("TEST 6: Create booking from quote & checks that the Booking's delivery is scheduled"
            + " using the DeliveryService")
    // Gathers quotes and then creates a unique booking for a customer's desired store
    // Test checks that the delivery service correctly schedules a delivery for the starting date
    // of the booking.
    void bookQuoteWDelivTest() {
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
        // Choose ediProvider3 to fulfill the order, and set to REQUIRING delivery
        // This will trigger the necessary logic to schedule the booking for delivery
        for (Quote quote:result) {
            if (quote.getProvider() == ediProvider3) {
                newBooking = quoteController.bookQuote(quote, true);
            }
        }
        
        // Get the bookings that are scheduled for the delivery day
        MockDeliveryService deliveryService = (MockDeliveryService) DeliveryServiceFactory.getDeliveryService();
        Collection<Deliverable> bookingsToBeDelivered = deliveryService.getPickupsOn(desiredDates.getStart());
        
        // Check that the newBooking just created is in the bookingsToBeDelivered
        assertTrue(bookingsToBeDelivered.contains(newBooking));
    } 
    
    @Test
    @DisplayName("TEST 7: Creates 2 bookings and verifies that these are recorded in *that Provider's* BookingController")
    // Gathers quotes and then creates a unique booking for a customer's desired store, twice
    // Test checks that the created bookings are present in the BookingController class
    void quoteBookingsCaptureTest() {
        /// FIRST QUOTE AND BOOKING: 1 MTB AND 1 STREET BIKE FROM EDIPROVIDER3
        // Setup the first query and add the bikes and desired quantities
        Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 1 MTB bike, 1 street bike
        desiredBikes.put(mountain, 1);
        desiredBikes.put(street, 1);
        
        // Get quotes in Edinburgh between 5th May 2020 and 15th May 2020, in EH postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2020, 5, 5), LocalDate.of(2020, 5, 15));     
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, 
                desiredBikes, new Location("EH3 XEY", "Gus Grissom Square, Edinburgh"));
        
        Booking firstBooking = null;
        // Choose ediProvider3 to fulfill the order, and set to not requiring delivery
        for (Quote quote:result) {
            if (quote.getProvider() == ediProvider3) {
                firstBooking = quoteController.bookQuote(quote, false);
            }
        }
        
        /// SECOND QUOTE AND BOOKING: 2 STREET BIKES FROM EDIPROVIDER3
        // Setup the second query and add the bikes and desired quantities
        Map<BikeType, Integer> desiredBikes2 = new HashMap<>();
        // Would like 2 street bikes
        desiredBikes2.put(street, 2);
        
        // Get quotes in Edinburgh between 12th June 2020 and 18th July 2020, in Glasgow postcodes
        DateRange desiredDates2 = new DateRange(LocalDate.of(2020, 6, 12), LocalDate.of(2020, 7, 18));     
        Set<Quote> result2 = quoteController.getQuotes(desiredDates2, scottishBikeProviders, 
                desiredBikes, new Location("EH8 9NP", "Chris Hadfield Street, Glasgow"));
        
        Booking secondBooking = null;
        // Choose ediProvider3 to fulfill the order, and set to not requiring delivery
        for (Quote quote:result2) {
            if (quote.getProvider() == ediProvider3) {
                secondBooking = quoteController.bookQuote(quote, false);
            }
        }
        
        Set<Booking> ediProvider3Bookings = ediProvider3.getBookingList();
        
        // Check whether the BookingController of ediProvider3 contains the 2 bookings made with that Provider
        assertTrue(ediProvider3Bookings.contains(firstBooking) && ediProvider3Bookings.contains(secondBooking));
    }
    
    /// FOLLOWING TESTS DEMONSTRATE THE FUNCTIONALITY OF THE RETURNING BIKE USE-CASE
    
    @Test
    @DisplayName("TEST 8: Test returning bikes to original store, checks status change throughout process")
    // Makes a booking that the customer then returns to original store
    // Ensures that the necessary steps are undertaken to change bike status during ALL stages of process
    void bookReturnBikeToOriginalProviderTest() {
        // Setup the first query and add the bikes and desired quantities
        Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 2 Street bikes
        desiredBikes.put(street, 2);
        
        // Get quotes in Glasgow between 20th May 2020 and 24th May 2020, in G2 postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 24));     
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, 
                desiredBikes, new Location("G2 EXTY", "Yuri Avenue, Glasgow"));
        
        Booking glasgowBooking = null;
        // Choose glasgowProvider1 to fulfill the order, and set to not requiring delivery
        for (Quote quote:result) {
            if (quote.getProvider() == glasgowProvider1) {
                glasgowBooking = quoteController.bookQuote(quote, false);
            }
        }
        
        // BOOKING DOES NOT REQUIRE DELIVERY
        glasgowBooking.onPickup(); // Pick up from the store, set booking to: BIKES_WITH_CUSTOMER, set bikes to: WITH_CUSTOMER
        
        // Checks that the status of the booking and the bikes are changed to WITH CUSTOMER representation when directly picked up
        assertEquals(Booking.Status.BIKES_WITH_CUSTOMER, glasgowBooking.getStatus());
        for (Bike bike:glasgowBooking.getBikeList()) {
            assertEquals(Bike.Status.WITH_CUSTOMER, bike.getBikeStatus());
        }
        
        // Customer brings bikes back to the original store, employee enters their UUID
        glasgowProvider1.recordBikeReturnToOriginalStore(glasgowBooking.getOrderNo());
        
        // Checks that the status of the booking and the bikes are changed to COMPLETE/IN STORE representation when directly picked up
        assertEquals(Booking.Status.COMPLETE, glasgowBooking.getStatus());
        for (Bike bike:glasgowBooking.getBikeList()) {
            assertEquals(Bike.Status.IN_STORE, bike.getBikeStatus());
        }
    }
    
    @Test
    @DisplayName("TEST 9: Test Delivery Scheduled when returning to PARTNER store, checks status change throughout process")
    // Makes a booking that the customer then returns to the partner store (ediProvider1)
    // Ensures that the necessary steps are undertaken to change bike status during ALL stages of process
    void bookReturnBikeToPartnerTest() {
        
        // Add ediProvider1 to glasgowProvider1's list of partner stores
        // Make the relationship 2-ways
        glasgowProvider1.addPartner(ediProvider1);
        ediProvider1.addPartner(glasgowProvider1);
        
        // Setup the first query and add the bikes and desired quantities
        Map<BikeType, Integer> desiredBikes = new HashMap<>();
        // Would like 1 BMX bike
        desiredBikes.put(bmx, 1);
        
        // Get quotes in Glasgow between 20th May 2020 and 24th May 2020, in G2 postcodes
        DateRange desiredDates = new DateRange(LocalDate.of(2020, 5, 20), LocalDate.of(2020, 5, 24));     
        Set<Quote> result = quoteController.getQuotes(desiredDates, scottishBikeProviders, 
                desiredBikes, new Location("G2 EXTY", "Chaffee Lane, Glasgow"));
        
        Booking glasgowBooking = null;
        // Choose glasgowProvider1 to fulfill the order, and set to not requiring delivery
        for (Quote quote:result) {
            if (quote.getProvider() == glasgowProvider1) {
                glasgowBooking = quoteController.bookQuote(quote, true);
            }
        }
        
        // BOOKING DOES REQUIRE DELIVERY
        glasgowBooking.onPickup(); // Pick up from the store, set booking to: BIKES_WITH_CUSTOMER, set bikes to: WITH_CUSTOMER
        
        // Checks that the status of the booking and the bikes are changed to WITH CUSTOMER representation when directly picked up
        assertEquals(Booking.Status.IN_TRANSIT_TO_CUSTOMER, glasgowBooking.getStatus());
        for (Bike bike:glasgowBooking.getBikeList()) {
            assertEquals(Bike.Status.IN_TRANSIT_TO_CUSTOMER, bike.getBikeStatus());
        }
        
        glasgowBooking.onDropoff(); // Drop off at the customer
        // Checks that the status of the booking and the bikes are changed to WITH CUSTOMER representation when directly picked up
        assertEquals(Booking.Status.BIKES_WITH_CUSTOMER, glasgowBooking.getStatus());
        for (Bike bike:glasgowBooking.getBikeList()) {
            assertEquals(Bike.Status.WITH_CUSTOMER, bike.getBikeStatus());
        }
        
        // Customer brings bikes back to the original store, employee enters their UUID
        ediProvider1.recordBikeReturnToPartnerStore(glasgowBooking);
        
        // Get the bookings that are scheduled for the end of the hire period, on the day the customer returns their bikes to the
        // other provider
        MockDeliveryService deliveryService = (MockDeliveryService) DeliveryServiceFactory.getDeliveryService();
        Collection<Deliverable> bookingsToBeDelivered = deliveryService.getPickupsOn(glasgowBooking.getHireDates().getEnd());
        
        // Check that a booking has been scheduled to send bike back to its ORIGINAL STORE on the end of the hire date
        assertTrue(bookingsToBeDelivered.contains(glasgowBooking));
    }
}
