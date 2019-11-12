package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {
    private Location location1;
    private Location location2;
    private Location location3;
    
    
    @BeforeEach
    void setUp() throws Exception {
        this.location1 = new Location("EH3 9NP", "4 Upper Gilmore Place");
        this.location2 = new Location("EH8 Y2K", "Edinburgh Castle");
        this.location3 = new Location("G2 1DU", "Glasgow City Chambers");
    }
    
    @BeforeAll
    static void beforeAll() {
        System.out.println("Starting tests on Location");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Completed Location test");
    }
    
    @Test
    @DisplayName("Same Postcode Test")
    void samePostcodeTest() {
        assertTrue(location1.isNearTo(location2));
    }

    @Test
    @DisplayName("Different Postcode Test")
    void differentPostcodeTest() {
        assertFalse(location1.isNearTo(location3));
    }
    
}
