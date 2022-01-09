package it.univpm.weather.WeatherApp.testModel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import it.univpm.weather.WeatherApp.model.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TestCity 
{
	@Mock
	private Temperature temp ;
	@Mock
	private City city;
	@Mock
	private Coordinates coord;
	private String json;
	
	@BeforeAll
	public void setUp()
	{
		temp = new Temperature(1641494587,6.78,5.39);
		coord = new Coordinates(43.617,13.5171);
		city = new City("Ancona", 3183087, 3600, coord, temp, null);
		json = "{\"dateTime\":1641494587,\"feelsLike\":6.78,\"temp\":5.39,\"cityName\":\"Ancona\",\"timeZone\":3600,\"lon\":13.5170982,\"cityId\":3183087,\"lat\":43.6170137}";;	
		
	}
	
	@Test
	public void testJSON()
	{
		assertEquals(city.toJson(),json);
	}
		
}
