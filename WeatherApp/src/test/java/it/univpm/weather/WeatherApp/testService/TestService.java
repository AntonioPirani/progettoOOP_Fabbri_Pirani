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
 * @author Antonio Pirani
 * @author Matteo Fabbri
 */
public class TestService 
{
	
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
		city = new City("Rimini", 3183087, 3600, coord, temp, null);
	}
	
	/** Test del metodo getTemperature.	 
	 * 
	 * @throws IOException
	 * @throws CityNotFoundException 
	 */
	@Test
	public void testGetTemperature() throws IOException, CityNotFoundException
	{
		assertNotNull(service.getTemperature("Ancona"));		
	}

	/** Test del metodo timeMachine.	 
	 * 
	 * @throws IOException
	 */
	@Test
	public void testTimeMachine() throws IOException
	{
		assertNotNull(service.timeMachine("Ancona", 1642534367)); //1642941424 23 gennaio
	}
	
	/** Test del metodo getCityCoords.	 
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
	
	/** Test del metodo saveCurrentTemp.	 
	 * 
	 * @throws IOException
	 * @throws HourException
	 */
	@Test
	public void testSaveCurrentTemp() throws IOException, HourException
	{
		assertNotNull(service.saveCurrentTemp(city));	//per evitare HourException cambiare nome alla city
	}
	
	/** Test del metodo compareTemp.	 
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
	
	/**
	 * Rilascio della risorsa
	 * 
	 * @throws Exception
	 */
	@AfterEach
	public void tearDown() throws Exception {
	}
	
}
