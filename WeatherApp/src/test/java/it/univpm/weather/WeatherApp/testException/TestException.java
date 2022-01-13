package it.univpm.weather.WeatherApp.testException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import it.univpm.weather.WeatherApp.exceptions.*;
import it.univpm.weather.WeatherApp.service.ServiceImplem;

public class TestException {
	
	private ServiceImplem service = new ServiceImplem();
	
	@BeforeEach
	public void setUp() throws Exception {
	}
	
	@Test
	public void testCityNotFoundException() {
		assertThrows(CityNotFoundException.class, () -> service.getCityCoords("Anconi"));	
	}
	
	@AfterEach
	public void tearDown() {	
	}

}
