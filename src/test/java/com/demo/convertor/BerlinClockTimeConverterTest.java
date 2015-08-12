package com.demo.convertor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.demo.convertor.BerlinClockTimeConverterImpl;
import com.demo.convertor.ConverterValidationException;
import com.demo.convertor.TimeConverter;

public class BerlinClockTimeConverterTest {

	TimeConverter converter;
	
	@Before
	public void init()
	{
		converter = new BerlinClockTimeConverterImpl();
	}
	
	@Test
	public void midnightPositive()
	{
		Assert.assertEquals("Y\r\nOOOO\r\nOOOO\r\nOOOOOOOOOOO\r\nOOOO", converter.convertTime("00:00:00"));
	}

	@Test
	public void midnightSpecialPositive()
	{
		Assert.assertEquals("Y\r\nRRRR\r\nRRRR\r\nOOOOOOOOOOO\r\nOOOO", converter.convertTime("24:00:00"));
	}

	@Test
	public void beforeMidnightPositive()
	{
		Assert.assertEquals("O\r\nRRRR\r\nRRRO\r\nYYRYYRYYRYY\r\nYYYY", converter.convertTime("23:59:59"));
	}
	
	@Test
	public void someTimePositive()
	{
		Assert.assertEquals("O\r\nRROO\r\nRRRO\r\nYYROOOOOOOO\r\nYYOO", converter.convertTime("13:17:01"));
	}

	@Test(expected=ConverterValidationException.class)
	public void timeFormatEmptyNegative()
	{
		Assert.assertEquals("O\nRROO\nRRRO\nYYROOOOOOOO\nYYOO\n", converter.convertTime(""));
	}

	@Test(expected=ConverterValidationException.class)
	public void timeWrongFormatNegative()
	{
		Assert.assertEquals("O\nRROO\nRRRO\nYYROOOOOOOO\nYYOO\n", converter.convertTime("10:10:10:10"));
	}

	@Test(expected=ConverterValidationException.class)
	public void hoursNotNumericNegative()
	{
		Assert.assertEquals("O\nRROO\nRRRO\nYYROOOOOOOO\nYYOO\n", converter.convertTime("1a:10:10"));
	}

	@Test(expected=ConverterValidationException.class)
	public void minutesNotNumericNegative()
	{
		Assert.assertEquals("O\nRROO\nRRRO\nYYROOOOOOOO\nYYOO\n", converter.convertTime("10:mm:10"));
	}

	@Test(expected=ConverterValidationException.class)
	public void secondsNotNumericNegative()
	{
		Assert.assertEquals("O\nRROO\nRRRO\nYYROOOOOOOO\nYYOO\n", converter.convertTime("10:10:ss"));
	}
	
	@Test(expected=ConverterValidationException.class)
	public void midnightNegative()
	{
		Assert.assertEquals("O\nRROO\nRRRO\nYYROOOOOOOO\nYYOO\n", converter.convertTime("24:00:01"));
	}

	@Test(expected=ConverterValidationException.class)
	public void hoursWrongValueNegative()
	{
		Assert.assertEquals("O\nRROO\nRRRO\nYYROOOOOOOO\nYYOO\n", converter.convertTime("25:00:00"));
	}

	@Test(expected=ConverterValidationException.class)
	public void minutesWrongValueNegative()
	{
		Assert.assertEquals("O\nRROO\nRRRO\nYYROOOOOOOO\nYYOO\n", converter.convertTime("23:61:00"));
	}

	@Test(expected=ConverterValidationException.class)
	public void secondsWrongValueNegative()
	{
		Assert.assertEquals("O\nRROO\nRRRO\nYYROOOOOOOO\nYYOO\n", converter.convertTime("23:00:61"));
	}

	
}
