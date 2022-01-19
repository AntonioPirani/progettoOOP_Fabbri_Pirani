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

/** La classe TestService testa i metodi della classe Service.
 * 
 * @author Matteo Fabbri
 */
public class TestService 
{
	//@Autowired
	private ServiceImplem service = new ServiceImplem();
	private Temperature temp ;
	private City city;
	private Coordinates coord;
	
	/** Setup dei parametri utilizzati nei vari metodi dell classe TestService.
	 */
	@Before
	public void setUp()
	{
		temp = new Temperature(1641494587,6.78,5.39);
		coord = new Coordinates(43.617,13.5171);
		city = new City("Ancona", 3183087, 3600, coord, temp, null);
	}
	
	/** Test del metodo testGetTemperature.	 
	 * 
	 * @throws IOException
	 * @throws CityNotFoundException 
	 */
	@Test
	public void testGetTemperature() throws IOException, CityNotFoundException
	{
		assertNotNull(service.getTemperature("Ancona"));		
	}

	/** Test del metodo testsettimeMachine.	 
	 * 
	 * @throws IOException
	 */
	@Test
	public void testTimeMachine() throws IOException
	{
		assertNotNull(service.timeMachine("Ancona", 1642534367));
	}
	
	/** Test del metodo testGetCityCoords.	 
	 * 
	 * @throws JsonParseException
	 * @throws IOException
	 * @throws CityNotFoundException
	 */
	@Test
	public void testGetCityCoords() throws JsonParseException, IOException, CityNotFoundException
	{
		assertNotNull(service.getCityCoords("Ancona"));	
	}
	
	/** Test del metodo testSaveCurrentTemp.	 
	 * 
	 * @throws IOException
	 * @throws HourException
	 */
	@Test
	public void testSaveCurrentTemp() throws IOException, HourException
	{
		assertNotNull(service.saveCurrentTemp(city));	
	}
	
	/** Test del metodo testCompareTemp.	 
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws CityNotFoundException 
	 */
	@Test
	public void testCompareTemp() throws IOException, ParseException, CityNotFoundException
	{
		assertNotNull(service.compareTemp("Ancona",1));		
	}
	
	@AfterEach
	public void tearDown() throws Exception {
	}
	
}
