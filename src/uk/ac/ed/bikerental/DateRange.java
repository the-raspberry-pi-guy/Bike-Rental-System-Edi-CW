package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * The DateRange class provides a powerful way to represent a collection of consecutive
 * dates. When given a start date and and end date in the LocalDate format, this class
 * represents those dates and all dates inbetween, allowing for things such as checking
 * for overlap.
 * 
 */
public class DateRange {
	/**
	 * The start date of the DateRange, in LocalDate format.
	 */
    private LocalDate start; 
    
    /**
     * The end date of the DateRange, in LocalDate format.
     */
    private LocalDate end;
    
    /**
     * The constructor for the DateRange - generates a DateRange object when given a start
     * and end date in the LocalDate format.
     * @param start The start date of the DateRange, in LocalDate format.
     * @param end The end date of the DateRange, in LocalDate format.
     */
    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }
    
    /**
     * A getter for the start variable.
     * @return The start date of the DateRange.
     */
    public LocalDate getStart() {
        return this.start;
    }
    
    /**
     * A getter for the end variable.
     * @return The end date of the DateRange.
     */
    public LocalDate getEnd() {
        return this.end;
    }
    
    /**
     * Implements the toString method for DateRange, allowing for representation of
     * the object in a string format, useful for debugging purposes.
     * @return A string containing the start and end fields of the DateRange.
     */
    @Override
    public String toString() {
        return "DateRange [start=" + start + ", end=" + end + "]";
    }
    
    /**
     * Converts a DateRange into the number of years between its start and end date, using
     * the Java ChronoUnit type.
     * @return The number of years between a start and end date.
     */
    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }
    
    /**
     * Converts a DateRange into the number of months between its start and end date, using
     * the Java ChronoUnit type.
     * @return The number of months between a start and end date.
     */
    public long toMonths() {
    	return ChronoUnit.MONTHS.between(this.getStart(), this.getEnd());
    }

    /**
     * Converts a DateRange into the number of days between its start and end date, using
     * the Java ChronoUnit type.
     * @return The number of days between a start and end date.
     */
    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }

    /**
     * Compares two DateRange objects and checks for any overlap between them, including
     * cases where the two DateRanges are equal, or have the same start/end date.
     * @param otherDate The date to compare to
     * @return A boolean value of whether or not there is overlap between the two DateRanges.
     */
    public Boolean overlaps(DateRange otherDate) {
        if (this.equals(otherDate)) {
            return true;
        } else {
            return (this.start.isBefore(otherDate.end) || this.start.isEqual(otherDate.end)) 
                    && (this.end.isAfter(otherDate.start) || this.end.isEqual(otherDate.start)); 
        }
    }
        
    /**
     * Implements the hashCode method for DateRange, allowing for use in collections.
     * @return A unique hashCode for the DateRange.
     */
    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }

    /**
     * Implements the equals method for DateRange, allowing for comparisons between objects.
     * Two DateRange objects are equal if they have the same start and end date.
     * @param obj The object to compare to.
     * @return A boolean value of whether or not the two objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        return Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }

}
