package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

@DisplayName("Linear Depreciation Test")
class LinearDepreciationTest {
    private BikeType typeBMX;
    private BikeType typeStreet;
    private Bike bikeBMX;
    private Bike bikeStreet;    
    
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting tests for Linear Depreciation");
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
        System.out.println("Completed Linear Depreciation test");
    }
 
    @Test
    @DisplayName("Provided Test")
    void providedTest() {
        // Linear Depreciation on a 3 year old BMX bike with starting value of £900
        System.out.println("Testing Linear Depreciation with supplied values");
        LinearDepreciation linearDep = new LinearDepreciation(new BigDecimal("10"));
        BigDecimal result = linearDep.calculateValue(bikeBMX, LocalDate.of(2008, 9, 6));
        assertEquals(new BigDecimal("630").stripTrailingZeros(), result.stripTrailingZeros());
    }
 
    @Test
    @DisplayName("Second Test")
    void secondTest() {
        // Linear Depreciation on a 7 year old street bike with starting value of £2000
        System.out.println("Testing Linear Depreciation with extra values");
        LinearDepreciation linearDep = new LinearDepreciation(new BigDecimal("10"));
        BigDecimal result = linearDep.calculateValue(bikeStreet, LocalDate.of(2019, 7, 6));
        assertEquals(new BigDecimal("600").stripTrailingZeros(), result.stripTrailingZeros());    
    }
    
    @Test
    @DisplayName("Depreciate Past 0 Test")
    void depreciateToZeroTest() {
        // 50% depreciation, so any bike of age 2 years or older should have depreciated to 0
        // Testing this on street bike, starting value £2000 after 4 years
        System.out.println("Testing Linear Depreciation going past 0");
        LinearDepreciation linearDep = new LinearDepreciation(new BigDecimal("50"));
        BigDecimal result = linearDep.calculateValue(bikeStreet, LocalDate.of(2016, 7, 6));
        assertEquals(new BigDecimal("0").stripTrailingZeros(), result.stripTrailingZeros());    
    }
}
