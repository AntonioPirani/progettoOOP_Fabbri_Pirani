package it.univpm.weather.service;

//import it.univpm.weather.exceptions.CityNotFound; TODO
import it.univpm.weather.model.City; 

import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * 
 * @author Antonio Pirani
 *
 */

public interface Service {
	
	public City getCityCoords(String cityName) throws IOException;
	
	public JSONObject getTemperature(City city) throws IOException, CityNotFoundException;
	
	public void saveEveryHour(String cityName) throws IOException;
	
}
