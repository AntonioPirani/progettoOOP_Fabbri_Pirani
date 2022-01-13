package it.univpm.weather.WeatherApp.testController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import it.univpm.weather.WeatherApp.exceptions.HourException;

/** La classe TestController testa i metodi della classe TempController.
 * 
 * @author Matteo Fabbri
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestController 
{
	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
	}

    /** Test del metodo testGetTemperature.
     * 
     * @throws Exception
     */
	@Test
	public void testGetTemperature() throws Exception
	{
		this.mockMvc.perform(get("/current")).andExpect(status().isOk());
	}
	
    /** Test del metodo testCompare.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void testCompare() throws Exception
	{
		this.mockMvc.perform(get("/current")).andExpect(status().isOk());
	}
	
	
    /** Test del metodo testStatistics.
	 * 
	 * @throws HourException
	 */
	@Test
	public void testStatistics() throws Exception 
	{
		this.mockMvc.perform(get("/current")).andExpect(status().isOk());
	}

//	@AfterEach
//	public void tearDown() throws Exception {
//	}
	
}
