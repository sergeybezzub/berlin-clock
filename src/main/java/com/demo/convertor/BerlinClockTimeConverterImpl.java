/**
 * Class represents a converting implementation of time in string format like a 'HH24:MM:SS' to the Berlin Clock format.
 * For more information about Berlin Clock format see https://en.wikipedia.org/wiki/Mengenlehreuhr
 */
package com.demo.convertor;

import static com.demo.convertor.constants.BerlinClockHoursRange.ADD_FOUR_HOURS;
import static com.demo.convertor.constants.BerlinClockHoursRange.MORE_THAN_TWENTY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.convertor.constants.BerlinClockHoursRange;
import com.demo.convertor.constants.BerlinClockMinutesRange;

public class BerlinClockTimeConverterImpl implements TimeConverter {

    private static final Logger LOG = LoggerFactory.getLogger(BerlinClockTimeConverterImpl.class);
    
	final private static String SECONDS_ON = "Y\r\n";
	final private static String SECONDS_OFF = "O\r\n";
	final private static String VK_PS = "\r\n";
	
	String[] minutesBerlinClockRange = new String[17];
	String[] hoursBerlinClockRange = new String[10];
	
	/**
	 * Converts time string to BerlinClock string
	 * 
	 * @param aTime
	 *            string in format 'HH:MM:SS'
	 * @return BerlinClock string
	 * @throw ConverterValidationException if input parameter is not correct
	 *        time string
	 */
	public String convertTime(String sTime) {

		/**
		 * Init meta data
		 * This data will be used during time parsing
		 */
		initData();
		
		/**
		 * Input validation
		 */
		String[] parts = inputValidation(sTime);

		int hours = parseHours(parts[0]);
		int minutes = parseMinutes(parts[1]);
		int seconds = parseSeconds(parts[2]);

		/**
		 * Validate Midnight special value
		 */
		if (hours == 24 && ( minutes != 0 || seconds != 0) ) {
			reportAndRethrowException("Incorrect time format! - '24:"+minutes+":"+seconds+"'");
		}
		
		/**
		 * Converting part
		 */
		StringBuilder builder = new StringBuilder();
		/**
		 * Calculate seconds
		 */
		builder.append(convertSeconds(seconds));

		/**
		 * Calculate hours
		 */
		if( hours == 24)
		{
			builder.append(MORE_THAN_TWENTY.getValue()).append(VK_PS).append(ADD_FOUR_HOURS.getValue()).append(VK_PS);
		}
		else
		{
			builder.append(convertHours(hours));
		}

		/**
		 * Calculate minutes
		 */
		builder.append(convertMinutes(minutes));

		
		return builder.toString();
	}

	private int parseSeconds(String part) {
		int seconds = 0;

		try {
			seconds = Integer.valueOf(part);
			validateSecondsValue(seconds);

		} catch (NumberFormatException e) {
			reportAndRethrowException("Input string for 'seconds' could be represented by integer value!");
		}
		return seconds;
	}

	private int parseMinutes(String part) {
		int minutes = 0;

		try {
			minutes = Integer.valueOf(part);
			validateMinutesValue(minutes);
		} catch (NumberFormatException e) {
			reportAndRethrowException("Input string for 'minutes' should be represented by integer value! "+e);
		}
		return minutes;
	}

	private int parseHours(String part) {
		int hours=0;
		try {
			hours = Integer.valueOf(part);
			validateHoursValue(hours);
		} catch (NumberFormatException e) {
			reportAndRethrowException("Input string for 'hours' should be represented by integer value!"+e);
		}
		return hours;
	}

	/**
	 * Convert hours to two rows for BerlinClock
	 * @param hours
	 * @return hours in Berlin format
	 */
	private String convertHours(int hours) {
		StringBuilder builder = new StringBuilder();

		/**
		 * Calculate row 1
		 */
		
		builder.append(buildFirstHoursRow(hours));

		/**
		 * Calculate row 2
		 */
		builder.append(buildSecondHoursRow(hours));
		
		return builder.toString();
	}

	/**
	 * Convert minutes to the BerlinClock format
	 * @param minutes
	 * @return minutes in BerlinClock format
	 */
	private String convertMinutes(int minutes) {
		StringBuilder builder = new StringBuilder();

		/**
		 * Calculate row 1
		 */
		builder.append(buildMinutesFirstRow(minutes));

		/**
		 * Calculate row 2
		 */
		builder.append(buildMinutesSecondRow(minutes));
		
		return builder.toString();
	}

	/**
	 * Convert seconds for BerlinClock format
	 * @param seconds
	 * @return 'Y' or 'O' symbol for odd or even seconds value is
	 */
	private String convertSeconds(int seconds) {
		return seconds % 2 == 0 ? SECONDS_ON : SECONDS_OFF;
	}

	/**
	 * Build first minutes row for BerlinClock  
	 * @param minutes
	 * @return first minutes row
	 */
	private String buildMinutesFirstRow(int minutes) {
	
		return minutesBerlinClockRange[minutes / 5] + VK_PS;
	}

	/**
	 * Build second minutes row for BerlinClock  
	 * @param minutes
	 * @return second minutes row
	 */
	private String buildMinutesSecondRow(int minutes) {
		return minutesBerlinClockRange[minutes % 5 + 12];
	}


	/**
	 * Build first hours row for BerlinClock  
	 * @param hours
	 * @return first hours row
	 */
	private String buildFirstHoursRow(int hours) {
		return hoursBerlinClockRange[hours / 5] + VK_PS;
	}

	/**
	 * Build second hours row for BerlinClock  
	 * @param hours
	 * @return second hours row
	 */
	private String buildSecondHoursRow(int hours) {
		return hoursBerlinClockRange[hours % 5 + 5] + VK_PS;
	}
	
	/**
	 * Validation of minutes or seconds values range
	 * @param value
	 * @return
	 */
	private boolean areMinutesOrSecondsValid(int value) {
		return (value < 0 || value > 59) ? false : true;
	}

	/**
	 * Validate if minutes have correct type
	 * @param minutes
	 */
	private void validateMinutesValue(int minutes) {
		if (!areMinutesOrSecondsValid(minutes)) {
			reportAndRethrowException("Input string minutes could be represented by integer value from 0 to 59 only!");
		}
	}

	/**
	 * Validate if seconds have correct type
	 * @param seconds
	 */
	private void validateSecondsValue(int seconds) {
		if (!areMinutesOrSecondsValid(seconds)) {
			reportAndRethrowException("Input string seconds could be represented by integer value from 0 to 59 only!");
		}
	}

	/**
	 * Validate if hours have correct type
	 * @param hours
	 */
	private void validateHoursValue(int hours) {
		if (hours < 0 || hours > 24) {
			reportAndRethrowException("Input string hours could be represented by integer value from 0 to 24 only!");
		}
	}

	/**
	 * Validate if input empty is not empty
	 * @param aTime
	 * @return
	 */
	private String[] inputValidation(String aTime) {
		if (aTime == null || aTime.length() == 0) {
			reportAndRethrowException("Input string time shouldn't be empty!");
		}

		String[] parts = aTime.split(":");

		if (parts == null || parts.length != 3) {
			reportAndRethrowException("Input string time should have correct string format 'HH:MM:SS'!");
		}
		return parts;
	}
	
	/**
	 * Default exception handling
	 * @param errMsg
	 */
	private void reportAndRethrowException(String errMsg) {
		LOG.error(errMsg);
		throw new ConverterValidationException(errMsg);
	}

	/**
	 * Put all possibles time values to arrays to be able to get it easily during time parsing
	 */
	private void initData()
	{
		for(BerlinClockMinutesRange value : BerlinClockMinutesRange.values())
		{
			minutesBerlinClockRange[value.ordinal()] = value.getValue();
		}
		
		for(BerlinClockHoursRange value : BerlinClockHoursRange.values())
		{
			hoursBerlinClockRange[value.ordinal()] = value.getValue();
		}
	}

}
