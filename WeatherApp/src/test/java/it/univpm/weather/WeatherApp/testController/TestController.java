package it.univpm.weather.WeatherApp.testController;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import it.univpm.weather.WeatherApp.controller.TempController;
import it.univpm.weather.WeatherApp.exceptions.HourException;

public class TestController 
{
	private TempController contro = new TempController();
	
	@Test
	public void testgetTemperature() throws HourException
	{
		assertNotNull(contro.getTemperature("Ancona"));		
	}
	
	@Test
	public void testCompare() throws IOException, ParseException 
	{
		assertNotNull(contro.compare("Ancona", 2));		
	}
	
	@Test
	public void testStatisttics() throws HourException 
	{
		assertNotNull(contro.statistics("Ancona", "", 0));		
	}
}
