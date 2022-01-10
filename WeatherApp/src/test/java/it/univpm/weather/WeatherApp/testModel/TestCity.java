package it.univpm.weather.WeatherApp.testModel;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import it.univpm.weather.WeatherApp.model.*;

public class TestCity 
{
	private Temperature temp ;
	private City city;
	private Coordinates coord;
	private String json;
	
	@Before
	public void setUp()
	{
		temp = new Temperature(1641494587,6.78,5.39);
		coord = new Coordinates(43.6170137,13.5170982);
		city = new City("Ancona", 3183087, 3600, coord, temp, null);
		json = "{\"dateTime\":1641494587,\"feelsLike\":5.39,\"temp\":6.78,\"cityName\":\"Ancona\",\"timeZone\":3600,\"lon\":13.5170982,\"cityId\":3183087,\"lat\":43.6170137}";	
		
	}
	
	@Test
	public void testJSON()
	{
		assertEquals(city.toJson().toString(),json);
	}
	
	@After
	void tearDown() throws Exception {
	}
		
}
