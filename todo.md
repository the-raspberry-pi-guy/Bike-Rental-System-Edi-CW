# To-Do Record
This To-Do Record was kept during the time that this project was being actively worked on as part of the University of Edinburgh's Inf2C - Software Engineering 2nd year course. Everything has been subsquently completed, kept for posterity:
---

- Implement DeliveryService - check the doc to just see if we have done correctly HARRY TO DO THIS
- - - -
# Comprehensive List of JUnit Tests to Implement

* Extension submodule tests: :white_check_mark: :white_check_mark: :white_check_mark:
  * Test linear depreciation :white_check_mark:
  * Test double declining balance depreciation :white_check_mark:
  * Test that bikes don't depreciate past 0 :white_check_mark:
    * (Ensure that the submodule is particularly correct, high quality in maintainability and readability)
    
* DateRange class tests: :white_check_mark: :white_check_mark: :white_check_mark:
  * Test the overlaps method returns correct boolean result for:
    * Overlapping period :white_check_mark:
    * Non-overlapping period :white_check_mark:
    * Single day period - dates on the same day :white_check_mark:
    * Overlapping dates the opposite way round :white_check_mark:
    
* Location class tests: :white_check_mark: :white_check_mark: :white_check_mark:
  * Test the isNearTo method returns correct boolean result for:
    * Locations that share the same 2-digit postcode start :white_check_mark:
    * Locations that don't share the same 2-digit postcode start :white_check_mark:

^^^ These tests are all to be handed-in

* DeliveryService class tests:
  * Test the scheduleDelivery method on objects implementing Deliverable interface:
    * Test onPickUp updates the status of bookings and the bikes they contain
    * Test onDropoff updates the status of bookings and the bikes they contain

### Tests by Use-Case - MATT TO FINISH OFF THE REST OF THE USECASE TESTS

These are tests that are important to demonstrate the success of a use-case:

1. Finding a quote: :white_check_mark: :white_check_mark: :white_check_mark:
  * Customer wants to find a quote for a booking on a given date range
  * System should return a list of all matching quotes, including: provider, bikes, price and deposit amounts
  * You should check that this EXCLUDES bikes which are already booked, and providers that are NOT near enough
    * System should test deposit valuation in standard and extended configuration

<em>Completed testing for first use-case, though want to add another test to check that bikes that have unavailable dates are not displayed. This first requires book quotes to be implemented.</em>
    
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
# Changes Needed to Coursework 2

#### UML Class Diagram :white_check_mark: :white_check_mark: :white_check_mark:
Classes:
* Add BikeType class to UML diagram, replacement value for the BikeType :white_check_mark:
* Implement partnership using associations (self-association) :white_check_mark:
* Record bike status in class :white_check_mark:
* Where do you keep "book information"? :white_check_mark:

Attributes & Methods:
* Consider each bike and booking has status - what data structure? DON'T use String - USE ENUMS :white_check_mark:

Associations:
* Consider partnership association (as in Classes) :white_check_mark:

#### UML Sequence Diagram: :white_check_mark: :white_check_mark: :white_check_mark:
* Use correct notation when calling/using method/operation of objects (Lecture 6 page 21) :white_check_mark:
* Keep in mind that the diagram shows interaction between classes and hence needs to call appropriate methods :white_check_mark:

Class Interactions in Sequence Diagram:
* Use colon before name of class (Lecture 6 page 21) :white_check_mark:
* DON'T use verbal things - write method names from UML class diagram :white_check_mark:

Iterative Behaviour in Sequence Diagram:
* Use loop/alt/opt for the if-else structure in design :white_check_mark:

#### UML Communication Diagram: :white_check_mark: :white_check_mark: :white_check_mark:
* Use formal method/operation calls as communication diagram is about dynamic interactions between objects - exchanging messages to each other (Lecture 6, page 16) :white_check_mark:
* Should change status of the bike :white_check_mark:

Same for the other diagrams

#### Integration of Design Extension :white_check_mark: :white_check_mark: :white_check_mark:
* DepositValuation should be related to another class rather than bike? Who is reponsible for these jobs (depsoit and price policies)?

#### Justification of Software Engineering Principles
* Add discussion about principles discussed in the course and refer where you applied these principles

- - - -

# Classes that are not worth testing/are tested in the overall system functionality:
> Bike, BikeType

- - - -

# Javadoc - HARRY DOES THIS

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

- - - -

# Things to change - HARRY TO CHANGE THIS, MATT TO CHANGE TESTS TO DO THIS

* Feed a customer object into the quote controller
