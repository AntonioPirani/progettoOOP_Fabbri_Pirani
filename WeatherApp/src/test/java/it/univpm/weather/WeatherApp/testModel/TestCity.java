package it.univpm.weather.WeatherApp.testModel;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import it.univpm.weather.WeatherApp.model.City;
import it.univpm.weather.WeatherApp.model.Coordinates;
import it.univpm.weather.WeatherApp.model.Temperature;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCity 
{
	private Temperature temp ;
	private City city;
	private Coordinates coord;
	
	@BeforeAll
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
		System.out.println(city.toJson());	
		System.out.println(json);	
		assertEquals(city.toJson(),json);
	}
		
}
