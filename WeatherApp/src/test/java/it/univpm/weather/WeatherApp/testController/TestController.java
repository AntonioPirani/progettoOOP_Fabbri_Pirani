package it.univpm.weather.WeatherApp.testController;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.univpm.weather.WeatherApp.controller.TempController;
import it.univpm.weather.WeatherApp.exceptions.HourException;

@AutoConfigureMockMvc
public class TestController 
{
	
    @Autowired
    private MockMvc mockMvc;
	
	@Test
	public void testGetTemperature() throws Exception
	{
		this.mockMvc.perform(MockMvcRequestBuilders.get("/current?cityName=Ancona")).andExpect(status().isOk());
	}
	
	@Test
	public void testCompare() throws IOException, ParseException 
	{
		//assertNotNull(contro.compare("Ancona", 2));		
	}
	
	@Test
	public void testStatistics() throws HourException 
	{
		//assertNotNull(contro.statistics("Ancona", "", 0));		
	}
}
