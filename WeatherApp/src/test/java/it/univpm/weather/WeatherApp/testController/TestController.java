package it.univpm.weather.WeatherApp.testController;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.univpm.weather.WeatherApp.controller.TempController;
import it.univpm.weather.WeatherApp.exceptions.HourException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestController 
{
	private TempController contro;
	
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
