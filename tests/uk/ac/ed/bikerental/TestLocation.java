package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {
    private Location location1;
    private Location location2;
    private Location location3;
    
    @BeforeAll
    static void startTests() {
        System.out.println("Starting tests for Location");
    }
    
    @BeforeEach
    void setUp() throws Exception {
        // 2 nearby locations and 1 not nearby location for testing
        this.location1 = new Location("EH3 9NP", "4, Upper Gilmore Place, Bruntsfield, Edinburgh");
        this.location2 = new Location("EH1 2NG", "Edinburgh Castle, Castlehill, Edinburgh");
        this.location3 = new Location("G2 1DU", "Glasgow City Chambers, 82 George Square, Glasgow");
    }
    
    @Test
    @DisplayName("Same Postcode Test")
    void samePostcodeTest() {
        // Test that location2 is near to location1
        assertTrue(location1.isNearTo(location2));
    }

    @Test
    @DisplayName("Different Postcode Test")
    void differentPostcodeTest() {
        // Test that location3 is not near to location1
        assertFalse(location1.isNearTo(location3));
    }
    
}
