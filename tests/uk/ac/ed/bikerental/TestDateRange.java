package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestDateRange {
    private DateRange dateRange1;
    private DateRange dateRange2;
    private DateRange dateRange3;
    
    private DateRange customRange1;
    private DateRange customRange2;
    private DateRange customRange3;
    private DateRange customRange4;
    private DateRange customRange5;
    private DateRange customRange6;
    private DateRange customRange7;
    private DateRange customRange8;
    private DateRange customRange9;
    private DateRange customRange10;
    
    @BeforeAll
    static void startTests() {
        System.out.println("Starting tests for DateRange");
    }
    
    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        // Three date ranges already provided for pre-made tests
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5), LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7), LocalDate.of(2018, 1, 10));
        
        // Date ranges to use in our custom tests
        this.customRange1 = new DateRange(LocalDate.of(2019, 5, 21), LocalDate.of(2019, 5, 28));
        this.customRange2 = new DateRange(LocalDate.of(2019, 5, 24), LocalDate.of(2019, 5, 31));
        this.customRange3 = new DateRange(LocalDate.of(2019, 5, 28), LocalDate.of(2019, 5, 28));
        this.customRange4 = new DateRange(LocalDate.of(2019, 5, 28), LocalDate.of(2019, 5, 28));
        this.customRange5 = new DateRange(LocalDate.of(2019, 5, 21), LocalDate.of(2019, 5, 28));
        this.customRange6 = new DateRange(LocalDate.of(2019, 5, 18), LocalDate.of(2019, 5, 23));
        this.customRange7 = new DateRange(LocalDate.of(2019, 5, 31), LocalDate.of(2019, 6, 2));
        this.customRange8 = new DateRange(LocalDate.of(2019, 5, 19), LocalDate.of(2019, 5, 21));
        this.customRange9 = new DateRange(LocalDate.of(2019, 5, 28), LocalDate.of(2019, 5, 31));
        this.customRange10 = new DateRange(LocalDate.of(2019, 5, 24), LocalDate.of(2019, 5, 26));
       
    }

    // Sample JUnit tests checking toYears works
    @Test
    @DisplayName("Test 0 Years Between DateRange")
    void testToYears0() {
        assertEquals(0, this.dateRange1.toYears());
    }

    @Test
    @DisplayName("Test 3 Years Between DateRange")
    void testToYears3() {
        assertEquals(3, this.dateRange3.toYears());
    }
   
    // Test all possible permutations of an overlap, including all edge cases
    @Test
    @DisplayName("Test Overlapping DateRange on Right Hand Side")
    void testOverlapsRHSTrue() {
        // Test customRange2 overlaps with customRange 1 (RHS overlap)
        assertTrue(customRange1.overlaps(customRange2));
    }
    
    @Test
    @DisplayName("Test Overlapping DateRange on Right Hand Side edge")
    void testOverlapsRHSEdgeTrue() {
        // Test customRange9 overlaps with customRange 1 (RHS overlap on edge)
        assertTrue(customRange1.overlaps(customRange9));
    }
    
    @Test
    @DisplayName("Test Overlapping DateRange on Left Hand Side")
    void testOverlapsLHSTrue() {
        // Test customRange2 overlaps with customRange 1 (LHS overlap)
        assertTrue(customRange1.overlaps(customRange6));
    }
    
    @Test
    @DisplayName("Test Overlapping DateRange on Left Hand Side edge")
    void testOverlapsLHSEdgeTrue() {
        // Test customRange8 overlaps with customRange 1 (LHS overlap on edge)
        assertTrue(customRange1.overlaps(customRange8));
    }
    
    @Test
    @DisplayName("Test Overlapping DateRange on inside of Range")
    void testOverlapsInsideTrue() {
        // Test customRange10 overlaps with customRange 1 (10 contained in 1)
        assertTrue(customRange1.overlaps(customRange10));
    }

    @Test
    @DisplayName("Test Non-Overlapping DateRange")
    void testOverlapsFalse() {
        // Check that customRange7 does not overlap with customRange1
        assertFalse(customRange1.overlaps(customRange7));
    }

    @Test
    @DisplayName("Test Directly Overlapping DateRange on Single Day")
    void testOverlapSingleDateRangeTrue() {
        // Check that single day customRange4 overlaps with single day customRange3
        assertTrue(customRange3.overlaps(customRange4));
    }

    @Test
    @DisplayName("Test Directly Overlapping DateRange on Range")
    void testDirectOverlapDateRangeTrue() {
        // Check that dateRange5 directly overlaps with dateRange1
        assertTrue(customRange1.overlaps(customRange5));
    } 
}
