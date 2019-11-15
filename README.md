# Bike-Rental-System
Code for implementation of a Scotland-wide Bike Rental System for the Inf2C Software Engineering 2nd year course at The University of Edinburgh

## List of things to do next
- Matt to implement last DateRange test and fix the backwards overlap
- Matt to test Harry's pricing code
- Harry to implement Book Quotes

### - Start working on bookQuotes - need to generate Bookings - come back for Discord
- Implement DeliveryService

- - - -
# Changes Needed to Coursework 2

#### UML Class Diagram
Classes:
* Add BikeType class to UML diagram, replacement value for the BikeType
* Implement partnership using associations (self-association)
* Record bike status in class
* Where do you keep "book information"?

Attributes & Methods:
* Consider each bike and booking has status - what data structure? DON'T use String

Associations:
* Consider partnership association (as in Classes)

#### UML Sequence Diagram:
* Use correct notation when calling/using method/operation of objects (Lecture 6 page 21)
* Keep in mind that the diagram shows interaction between classes and hence needs to call appropriate methods

Class Interactions in Sequence Diagram:
* Use colon before name of class (Lecture 6 page 21)
* DON'T use verbal things - write method names from UML class diagram

Iterative Behaviour in Sequence Diagram:
* Use loop/alt/opt for the if-else structure in design

#### UML Communication Diagram:
* Use formal method/operation calls as communication diagram is about dynamic interactions between objects - exhcnaging messages to each other (Lecture 6, page 16)
* Should change status of the bike

Same for the other diagrams

#### Integration of Design Extension
* DepositValuation should be related to another class rather than bike? Who is reponsible for these jobds (depsoit and price policies)?

#### Justification of Software Engineering Principles
* Add discussion about principles discussed in the course and refer where you applied these principles

- - - -

# Comprehensive List of JUnit Tests to Implement

* Extension submodule tests:
  * Test linear depreciation :white_check_mark:
  * Test double declining balance depreciation :white_check_mark:
  * Test that bikes don't depreciate past 0 :white_check_mark:
    * (Ensure that the submodule is particularly correct, high quality in maintainability and readability)
    
* DateRange class tests:
  * Test the overlaps method returns correct boolean result for:
    * Overlapping period :white_check_mark:
    * Non-overlapping period :white_check_mark:
    * Single day period - dates on the same day :white_check_mark:
    * Overlapping dates the opposite way round
    
* Location class tests:
  * Test the isNearTo method returns correct boolean result for:
    * Locations that share the same 2-digit postcode start
    * Locations that don't share the same 2-digit postcode start

^^^ These tests are all to be handed-in

* DeliveryService class tests:
  * Test the scheduleDelivery method on objects implementing Deliverable interface:
    * Test onPickUp updates the status of bookings and the bikes they contain
    * Test onDropoff updates the status of bookings and the bikes they contain

### Tests by Use-Case

These are tests that are important to demonstrate the success of a use-case:

1. Finding a quote:
  * Customer wants to find a quote for a booking on a given date range
  * System should return a list of all matching quotes, including: provider, bikes, price and deposit amounts
  * You should check that this EXCLUDES bikes which are already booked, and providers that are NOT near enough
    * System should test deposit valuation in standard and extended configuration
    
2. Booking a quote:
  * Customer wants to book a quote the system returned in 1.
  * Should place a booking with the provider listed, matching the details of the quote requested
  * Return an object representing the details of the booking, including unique booking number
  * If customer has requested delivery, DeliveryService should be used to schedule it.
  
3. Returning bikes:
  * Customer has arrived at bike provider to drop off their bikes
  * Bike provider employee handles return with booking number:
    * If original bike provider, then process this
    * If partner return, process this and use DeliveryService to return bikes to original provider

Other notes:
* "May want to include tests to check the integration of the modules of the system"
* SystemTest.java serves as primary evidence of correct function of system

- - - -

# Classes that are not worth testing/are tested in the overall system functionality:
> Bike, BikeType

- - - -

# Javadoc

Add class-level, method-level and field-level Javadoc comments to:

* Location
* DateRange
  * For methods you only need to include @param and @return tags as well as a summary of the role of the method
  * Include a summary sentence at the start of each comment separated by a blank line from rest of the comment
  
- - - -

# Error-catching and Assertions

Include some assertions in at least some of your methods as a means of catching faults and invalid states. Only need 1/2 to show how to use them:

Ideas:
* Catching that the dates trying to rent are not before the actual purchase date of the bike
* Catching invalid postcodes and location information
* More suggestions...
