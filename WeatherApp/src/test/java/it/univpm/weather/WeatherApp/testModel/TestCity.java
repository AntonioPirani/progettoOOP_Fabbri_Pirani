package it.univpm.weather.WeatherApp.testModel;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import it.univpm.weather.WeatherApp.model.City;
import it.univpm.weather.WeatherApp.model.Coordinates;
import it.univpm.weather.WeatherApp.model.Temperature;

public class TestCity 
{
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
	public void testJSON()
	{
		String json = "{\"dateTime\":1641494587,\"feelsLike\":6.78,\"temp\":5.39,\"cityName\":\"Ancona\",\"timeZone\":3600,\"lon\":13.5170982,\"cityId\":3183087,\"lat\":43.6170137}";
		
		assertEquals(city.toJson(),json);
	}
		
}
