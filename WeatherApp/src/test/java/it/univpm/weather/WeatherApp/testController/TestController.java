package it.univpm.weather.WeatherApp.testController;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.univpm.weather.WeatherApp.controller.TempController;
import it.univpm.weather.WeatherApp.exceptions.HourException;

/** La classe TestController testa i metodi della classe TempController.
 * 
 * @author Matteo Fabbri
 */

@AutoConfigureMockMvc
public class TestController 
{
	private TempController contro = new TempController();

    @Autowired
    private MockMvc mockMvc;
	
    /** Test del metodo testGetTemperature.
     * 
     * @throws Exception
     */
	@Test
	public void testGetTemperature() throws Exception
	{
		this.mockMvc.perform(MockMvcRequestBuilders.get("/current?cityName=Ancona")).andExpect(status().isOk());
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
}
