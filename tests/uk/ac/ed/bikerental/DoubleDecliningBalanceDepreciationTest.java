package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

@DisplayName("Double Declining Balance Depreciation Test")
class DoubleDecliningBalanceDepreciationTest { 
    private BikeType typeBMX;
    private BikeType typeStreet;
    private Bike bikeBMX;
    private Bike bikeStreet;
    
    @BeforeAll
    static void startTests() {
        System.out.println("Starting tests for Double Declining Balance Depreciation");
    }
    
    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        // Create new BMX bike valued at £900 and from 2005
        this.typeBMX = new BikeType("BMX", new BigDecimal("900"));
        this.bikeBMX = new Bike(this.typeBMX, LocalDate.of(2005, 8, 19));
        
        // Create new Street bike valued at £2000 and from 2012
        this.typeStreet = new BikeType("Street", new BigDecimal("2000"));
        this.bikeStreet = new Bike(this.typeStreet, LocalDate.of(2012, 5, 2)); 
    }
    
    @AfterAll
    static void afterAll() {
        System.out.println("Completed Double Declining Balance Depreciation Test");
    }
 
    @Test
    @DisplayName("Provided Test")
    void providedTest() {
        // Double Declining Balance Depreciation on a 3 year old BMX bike with starting value of £900
        System.out.println("Testing Double Declining Balance Depreciation with supplied values");
        DoubleDecliningBalanceDepreciation ddbd = new DoubleDecliningBalanceDepreciation(new BigDecimal("10"));
        BigDecimal result = ddbd.calculateValue(bikeBMX, LocalDate.of(2008, 9, 6));
        assertEquals(new BigDecimal("460.8").stripTrailingZeros(), result.stripTrailingZeros());
    }
 
    @Test
    @DisplayName("Second test")
    void secondTest() {
        // Double Declining Balance Depreciation on a 2 year old street bike with starting value of £2000
        System.out.println("Testing Double Declining Balance Depreciation with extra values");
        DoubleDecliningBalanceDepreciation ddbd = new DoubleDecliningBalanceDepreciation(new BigDecimal("15"));
        BigDecimal result = ddbd.calculateValue(bikeStreet, LocalDate.of(2014, 12, 6));
        assertEquals(new BigDecimal("980").stripTrailingZeros(), result.stripTrailingZeros());
    }
    
    @Test
    @DisplayName("Depreciate Past 0 Test")
    void depreciateToZeroTest() {
        // 60% depreciation after 9 years on street bike valued originally at £2000, should depreciate to zero
        System.out.println("Testing Double Declining Depreciation going past 0");
        DoubleDecliningBalanceDepreciation ddbd = new DoubleDecliningBalanceDepreciation(new BigDecimal("60"));
        BigDecimal result = ddbd.calculateValue(bikeStreet, LocalDate.of(2021, 7, 6));
        assertEquals(new BigDecimal("0").stripTrailingZeros(), result.stripTrailingZeros());    
    }    
}