package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

@DisplayName("Linear Depreciation Test")
class LinearDepreciationTest {
  
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
        System.out.println("Testing Linear Depreciation with supplied values");
        BikeType type = new BikeType("BMX", new BigDecimal("900"));
        Bike bike = new Bike(type, LocalDate.of(2005, 8, 19));
        LinearDepreciation linearDep = new LinearDepreciation(new BigDecimal("10"));
        BigDecimal result = linearDep.calculateValue(bike, LocalDate.of(2008, 4, 6));
        assertEquals(new BigDecimal("630").stripTrailingZeros(), result.stripTrailingZeros());
    }
 
    @Test
    @DisplayName("Second test")
    void secondTest() {
        System.out.println("Second test method");
    }
}
