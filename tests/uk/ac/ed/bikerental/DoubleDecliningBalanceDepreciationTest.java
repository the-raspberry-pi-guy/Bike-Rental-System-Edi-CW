package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

@DisplayName("Double Declining Balance Depreciation Test")
class DoubleDecliningBalanceDepreciationTest {
    // Create new BMX bike valued at £900 and from 2005
    BikeType typeBMX = new BikeType("BMX", new BigDecimal("900"));
    Bike bikeBMX = new Bike(typeBMX, LocalDate.of(2005, 8, 19));
    
    // Create new Street bike valued at £2000 and from 2012
    BikeType typeStreet = new BikeType("Street", new BigDecimal("2000"));
    Bike bikeStreet = new Bike(typeStreet, LocalDate.of(2012, 5, 2));  
    
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting tests on Double Declining Balance Depreciation");
    }
    
    @AfterAll
    static void afterAll() {
        System.out.println("Completed Double Declining Balance Depreciation Test");
    }
 
    @Test
    @DisplayName("Provided Test")
    void providedTest() {
        // 3 year old bike with starting value of £900
        System.out.println("Testing Double Declining Balance Depreciation with supplied values");
        DoubleDecliningBalanceDepreciation ddbd = new DoubleDecliningBalanceDepreciation(new BigDecimal("10"));
        BigDecimal result = ddbd.calculateValue(bikeBMX, LocalDate.of(2008, 9, 6));
        assertEquals(new BigDecimal("460.8").stripTrailingZeros(), result.stripTrailingZeros());
    }
 
    @Test
    @DisplayName("Second test")
    void secondTest() {
        System.out.println("Testing Double Declining Balance Depreciation with extra values");
        DoubleDecliningBalanceDepreciation ddbd = new DoubleDecliningBalanceDepreciation(new BigDecimal("15"));
        BigDecimal result = ddbd.calculateValue(bikeStreet, LocalDate.of(2014, 12, 6));
        assertEquals(new BigDecimal("980").stripTrailingZeros(), result.stripTrailingZeros());
    }
    
    @Test
    @DisplayName("Depreciate Past 0 Test")
    void depreciateToZeroTest() {
        // 60% depreciation after 9 years, will depreciate to nothing
        System.out.println("Testing Double Declining Depreciation going past 0");
        DoubleDecliningBalanceDepreciation ddbd = new DoubleDecliningBalanceDepreciation(new BigDecimal("60"));
        BigDecimal result = ddbd.calculateValue(bikeStreet, LocalDate.of(2021, 7, 6));
        assertEquals(new BigDecimal("0").stripTrailingZeros(), result.stripTrailingZeros());    
    }    
}