package it.univpm.weather.WeatherApp.testModel;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import it.univpm.weather.WeatherApp.model.*;

/** La classe TestCity testa i metodi della classe City.
 * 
 * @author Matteo Fabbri
 */
public class TestCity 
{
	private Temperature temp ;
	private City city;
	private Coordinates coord;
	private String json;
	
	/** Setup dei parametri utilizzati nella classe City.
	 * 
	 */
	@Before
	public void setUp()
	{
		temp = new Temperature(1641494587,6.78,5.39);
		coord = new Coordinates(43.6170137,13.5170982);
		city = new City("Ancona", 3183087, 3600, coord, temp, null);
		json = "{\"dateTime\":1641494587,\"feelsLike\":5.39,\"temp\":6.78,\"cityName\":\"Ancona\",\"timeZone\":3600,\"lon\":13.5170982,\"cityId\":3183087,\"lat\":43.6170137}";	
		
	}
	
    /** Test del metodo testJSON. 
	 */
	@Test
	public void testJSON()
	{
		assertEquals(city.toJson().toString(),json);
	}
	
	/**
	 * Rilascio della risorsa
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
		
}
