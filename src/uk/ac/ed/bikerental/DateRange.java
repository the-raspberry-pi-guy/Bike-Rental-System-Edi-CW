package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateRange {
	private LocalDate start, end;
	
	public DateRange(LocalDate start, LocalDate end) {
		this.start = start;
		this.end = end;
	}
	
	public LocalDate getStart() {
		return this.start;
	}
	
	public LocalDate getEnd() {
		return this.end;
	}

	public long toYears() {
		return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
	}
	
	// You can add your own methods here
}
