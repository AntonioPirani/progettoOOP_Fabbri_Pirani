package it.univpm.weather.WeatherApp.service;

import it.univpm.weather.WeatherApp.exceptions.*;
import it.univpm.weather.WeatherApp.model.*;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;
/**
 * 
 * @author Antonio Pirani
 *
 */

public interface Service {
	
	public City getTemperature(String cityName) throws IOException, CityNotFoundException; //current
	
	public JSONArray timeMachine(String cityName, long dt) throws IOException;
	
	public City getCityCoords(String cityName) throws IOException, JsonParseException, CityNotFoundException; //oneCall
	
	public boolean saveCurrentTemp(City city) throws IOException, HourException;
	
	public void saveEveryHour(String cityName) throws IOException;
	
	public String compareTemp(String cityName, int prevDay) throws IOException, ParseException; //oneCall
	
}
