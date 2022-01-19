package it.univpm.weather.WeatherApp.testException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import it.univpm.weather.WeatherApp.exceptions.*;
import it.univpm.weather.WeatherApp.service.ServiceImplem;

/**
 * Classe di test per verificare la correttezza delle eccezioni
 * 
 * @author Antonio Pirani
 * @author Matteo Fabbri
 *
 */
public class TestException {
	
	private ServiceImplem service = new ServiceImplem();
	
	/**
	 * SetUp del test
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
	}
	
	/**
	 * Test che verifica se viene lanciata la CityNotFoundException se viene inserita una città
	 * non valida
	 */
	@Test
	public void testCityNotFoundException() {
		assertThrows(CityNotFoundException.class, () -> service.getCityCoords("Anconi"));	
	}
	
	/**
	 * Rilascio della risorsa
	 */
	@AfterEach
	public void tearDown() {	
	}

}
