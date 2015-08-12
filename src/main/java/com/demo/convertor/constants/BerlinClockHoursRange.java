package com.demo.convertor.constants;

public enum BerlinClockHoursRange {

	LESS_THAN_FIVE("OOOO"), 
	MORE_THAN_FIVE_BUT_LESS_THAN_TEN("ROOO"), 
	MORE_THAN_TEN_BUT_LESS_THAN_FIFTEEN("RROO"), 
	MORE_THAN_FIFTEEN_BUT_LESS_THAN_TWENTY("RRRO"), 
	MORE_THAN_TWENTY("RRRR"), 
	ADD_ZERO_HOURS("OOOO"), 
	ADD_ONE_HOUR("ROOO"), 
	ADD_TWO_HOURS("RROO"), 
	ADD_THREE_HOURS("RRRO"), 
	ADD_FOUR_HOURS("RRRR");

	private String value;

	private BerlinClockHoursRange(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
