package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

@DisplayName("Double Declining Balance Depreciation Test")
class DoubleDecliningBalanceDepreciationTest {
  
    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all test methods");
    }
 
    @BeforeEach
    void beforeEach() {
        System.out.println("Before each test method");
    }
 
    @AfterEach
    void afterEach() {
        System.out.println("After each test method");
    }
 
    @AfterAll
    static void afterAll() {
        System.out.println("After all test methods");
    }
 
    @Test
    @DisplayName("First test")
    void firstTest() {
        System.out.println("Testing Double Declining Balance Depreciation with supplied values");
        Bike bike = new Bike("BMX", new BigDecimal("900"), LocalDate.of(2005, 8, 19));
        DoubleDecliningBalanceDepreciation ddbd = new DoubleDecliningBalanceDepreciation(new BigDecimal("10"));
        BigDecimal result = ddbd.calculateValue(bike, LocalDate.of(2008, 4, 6));
        assertEquals(new BigDecimal("460.8").stripTrailingZeros(), result.stripTrailingZeros());
    }
 
    @Test
    @DisplayName("Second test")
    void secondTest() {
        System.out.println("Testing Double Declining Balance Depreciation with extra values");
        Bike bike = new Bike("Street", new BigDecimal("1500"), LocalDate.of(2003, 8, 19));
        DoubleDecliningBalanceDepreciation ddbd = new DoubleDecliningBalanceDepreciation(new BigDecimal("30"));
        BigDecimal result = ddbd.calculateValue(bike, LocalDate.of(2008, 4, 6));
        assertEquals(new BigDecimal("15.36").stripTrailingZeros(), result.stripTrailingZeros());
    }
}