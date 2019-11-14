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
    private DateRange dateRange4;
    private DateRange dateRange5;
    private DateRange dateRange6;
    private DateRange dateRange7;

    @BeforeAll
    static void startTests() {
        System.out.println("Starting tests for DateRange");
    }
    
    @BeforeEach
    void setUp() throws Exception {
        // Setup resources before each test
        // Three date ranges, with dateRange1 and dateRange2 clearly overlapping
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7), LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5), LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7), LocalDate.of(2018, 1, 10));
        // Multiple single day dateRanges to test edge-case functionality
        this.dateRange4 = new DateRange(LocalDate.of(2019, 5, 21), LocalDate.of(2019, 5, 21));
        this.dateRange5 = new DateRange(LocalDate.of(2019, 5, 21), LocalDate.of(2019, 5, 21));
        // Multiple same days dateRanges to test directly overlapping functionality
        this.dateRange6 = new DateRange(LocalDate.of(2019, 8, 18), LocalDate.of(2019, 8, 25));
        this.dateRange7 = new DateRange(LocalDate.of(2019, 8, 18), LocalDate.of(2019, 8, 25));
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

    @Test
    @DisplayName("Test Overlapping DateRange")
    void testOverlapsTrue() {
        // Check that dateRange2 overlaps with dateRange1
        assertTrue(dateRange1.overlaps(dateRange2));
    }

    @Test
    @DisplayName("Test Non-Overlapping DateRange")
    void testOverlapsFalse() {
        // Check that dateRange3 does not overlap with dateRange1
        assertFalse(dateRange1.overlaps(dateRange3));
    }
    
    @Test
    @DisplayName("Test Directly Overlapping DateRange on Single Day")
    void testOverlapSingleDateRangeTrue() {
        // Check that single day dateRange4 overlaps with dateRange5
        assertTrue(dateRange4.overlaps(dateRange5));
    }
    
    @Test
    @DisplayName("Test Directly Overlapping DateRange on Range")
    void testDirectOverlapDateRangeTrue() {
        // Check that single day dateRange4 overlaps with dateRange5
        assertTrue(dateRange6.overlaps(dateRange7));
    }
}
