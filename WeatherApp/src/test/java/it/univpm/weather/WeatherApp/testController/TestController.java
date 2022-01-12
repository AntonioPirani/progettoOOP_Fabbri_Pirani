package it.univpm.weather.WeatherApp.testController;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import it.univpm.weather.WeatherApp.controller.TempController;
import it.univpm.weather.WeatherApp.exceptions.HourException;

/** La classe TestController testa i metodi della classe TempController.
 * 
 * @author Matteo Fabbri
 */

@AutoConfigureMockMvc
public class TestController 
{
	
	TempController contro = new TempController();

	@BeforeEach
	void setUp() throws Exception {
	}

    /** Test del metodo testGetTemperature.
     * 
     * @throws Exception
     */
	@Test
	public void testGetTemperature() throws Exception
	{
		assertNotNull(contro.getTemperature("Ancona"));
	}
	
    /** Test del metodo testCompare.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void testCompare() throws IOException, ParseException 
	{
		assertNotNull(contro.compare("Ancona", 2));		
	}
	
	
    /** Test del metodo testStatistics.
	 * 
	 * @throws HourException
	 */
	@Test
	public void testStatistics() throws HourException 
	{
		assertNotNull(contro.statistics("Ancona", "", 0));		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
}
