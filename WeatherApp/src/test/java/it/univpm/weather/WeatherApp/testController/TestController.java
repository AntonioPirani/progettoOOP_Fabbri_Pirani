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
 * @author Antonio Pirani
 * @author Matteo Fabbri
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestController 
{
	@Autowired
	private MockMvc mockMvc;

	/**
	 * SetUp del test - autoconfigurato grazie all'annotazione AutoConfigureMockMvc
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

    /**
     * Test della chiamata /current.
     * 
     * @throws Exception
     */
	@Test
	public void testCurrent() throws Exception
	{
		this.mockMvc.perform(get("/current")).andExpect(status().isOk());
	}
	
    /**
     * Test della chiamata /compare.
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@Test
	public void testCompare() throws Exception
	{
		this.mockMvc.perform(get("/compare")).andExpect(status().isOk());
	}
	
	
    /**
     * Test della chiamata /statistics.
	 * 
	 * @throws HourException
	 */
	@Test
	public void testStatistics() throws Exception 
	{
		this.mockMvc.perform(get("/statistics")).andExpect(status().isOk());
	}
	
}
