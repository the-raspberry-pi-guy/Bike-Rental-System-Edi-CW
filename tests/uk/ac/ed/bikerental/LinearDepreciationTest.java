package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

@DisplayName("Linear Depreciation Test")
class LinearDepreciationTest {
    // Create new BMX bike valued at £900 and from 2005
    BikeType typeBMX = new BikeType("BMX", new BigDecimal("900"));
    Bike bikeBMX = new Bike(typeBMX, LocalDate.of(2005, 8, 19));
    
    // Create new Street bike valued at £2000 and from 2012
    BikeType typeStreet = new BikeType("Street", new BigDecimal("2000"));
    Bike bikeStreet = new Bike(typeStreet, LocalDate.of(2012, 5, 2));
    
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting tests on Linear Depreciation");
    }

 
    @AfterAll
    static void afterAll() {
        System.out.println("Completed Linear Depreciation test");
    }
 
    @Test
    @DisplayName("Provided Test")
    void providedTest() {
        // 3 year old bike with starting value of £900
        System.out.println("Testing Linear Depreciation with supplied values");
        LinearDepreciation linearDep = new LinearDepreciation(new BigDecimal("10"));
        BigDecimal result = linearDep.calculateValue(bikeBMX, LocalDate.of(2008, 9, 6));
        assertEquals(new BigDecimal("630").stripTrailingZeros(), result.stripTrailingZeros());
    }
 
    @Test
    @DisplayName("Custom Test")
    void secondTest() {
        System.out.println("Testing Linear Depreciation with custom values");
        LinearDepreciation linearDep = new LinearDepreciation(new BigDecimal("10"));
        BigDecimal result = linearDep.calculateValue(bikeStreet, LocalDate.of(2019, 7, 6));
        assertEquals(new BigDecimal("600").stripTrailingZeros(), result.stripTrailingZeros());    
    }
    
    @Test
    @DisplayName("Depreciate Past 0 Test")
    void depreciateToZeroTest() {
        // 50% depreciation, so any bike of age 2 years or older should have depreciated to 0
        System.out.println("Testing Linear Depreciation going past 0");
        LinearDepreciation linearDep = new LinearDepreciation(new BigDecimal("50"));
        BigDecimal result = linearDep.calculateValue(bikeStreet, LocalDate.of(2016, 7, 6));
        assertEquals(new BigDecimal("0").stripTrailingZeros(), result.stripTrailingZeros());    
    }
}
