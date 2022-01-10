package it.univpm.weather.WeatherApp.testService;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.json.JsonParseException;

import it.univpm.weather.WeatherApp.exceptions.CityNotFoundException;
import it.univpm.weather.WeatherApp.exceptions.HourException;
import it.univpm.weather.WeatherApp.model.City;
import it.univpm.weather.WeatherApp.model.Coordinates;
import it.univpm.weather.WeatherApp.model.Temperature;
import it.univpm.weather.WeatherApp.service.*;

public class TestService 
{
	//@Autowired
	private ServiceImplem service = new ServiceImplem();
	private Temperature temp ;
	private City city;
	private Coordinates coord;
	
	@Before
	public void setUp()
	{
		temp = new Temperature(1641494587,6.78,5.39);
		coord = new Coordinates(43.617,13.5171);
		city = new City("Ancona", 3183087, 3600, coord, temp, null);
	}
	
	@Test
	public void testgetTemperature() throws IOException
	{
		assertNotNull(service.getTemperature("Ancona"));		
	}

	@Test
	public void testsettimeMachine() throws IOException
	{
		assertNotNull(service.timeMachine("Ancona", 1641494587));
	}
	
	@Test
	public void testgetCityCoords() throws JsonParseException, IOException, CityNotFoundException
	{
		assertNotNull(service.getCityCoords("Ancona"));	
	}
	
	@Test
	public void testsaveCurrentTemp() throws IOException, HourException
	{
		assertNotNull(service.saveCurrentTemp(city));	
	}
	
	@Test
	public void testcompareTemp() throws IOException, ParseException
	{
		assertNotNull(service.compareTemp("Ancona",1));		
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}
	
}
