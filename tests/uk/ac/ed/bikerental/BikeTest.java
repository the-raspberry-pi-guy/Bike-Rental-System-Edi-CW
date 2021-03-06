package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BikeTest {

    private Bike bike1;
    private Bike bike2;
    private Bike bike3;
    
    @BeforeEach
    void setUp() throws Exception {
        BikeType typeBMX = new BikeType("BMX", new BigDecimal("900"));
        BikeType typeStreet = new BikeType("Street", new BigDecimal("3000"));
        this.bike1 = new Bike(typeBMX, LocalDate.of(2010, 5, 1));
        this.bike2 = new Bike(typeBMX, LocalDate.of(2014, 8, 6));
        this.bike3 = new Bike(typeStreet, LocalDate.of(2008, 12, 25));
        
        bike1.makeUnavailable(new DateRange(LocalDate.of(2019, 8, 10), LocalDate.of(2019, 12, 13)));
        bike1.makeUnavailable(new DateRange(LocalDate.of(2019, 12, 16), LocalDate.of(2019, 12, 18)));
        bike3.makeUnavailable(new DateRange(LocalDate.of(2019, 5, 1), LocalDate.of(2019, 5, 3)));
    }
    
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting tests on Bike");
    }
    
    @Test
    @DisplayName("Unavailable Bike Test")
    void unavailableBikeTest() {
        assertFalse(bike3.isAvailable(new DateRange(LocalDate.of(2019, 5, 1), LocalDate.of(2019, 5, 3))));
    }
    
    @Test
    @DisplayName("Available Bike Test on Empty Range")
    void availableEmptyBikeTest() {
        assertTrue(bike2.isAvailable(new DateRange(LocalDate.of(2017, 5, 1), LocalDate.of(2017, 6, 3))));
    }

    @Test
    @DisplayName("Available Bike Test on Bike with Some Dates")
    void availableBikeTest() {
        assertTrue(bike1.isAvailable(new DateRange(LocalDate.of(2019, 12, 14), LocalDate.of(2019, 12, 15))));
    }
}
