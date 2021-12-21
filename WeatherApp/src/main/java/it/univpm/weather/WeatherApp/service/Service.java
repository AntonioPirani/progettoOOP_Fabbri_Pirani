package it.univpm.weather.WeatherApp.service;

import it.univpm.weather.WeatherApp.exceptions.CityNotFoundException;
import it.univpm.weather.WeatherApp.model.Coordinates;
import it.univpm.weather.WeatherApp.model.*; 

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * 
 * @author Antonio Pirani
 *
 */

public interface Service {
	
	public JSONObject getTemperature(String cityName) throws IOException; //current
	
	public Coordinates getCityCoords(String cityName) throws IOException, JsonParseException, CityNotFoundException; //oneCall
	
	public void saveCurrentTemp(String cityName) throws IOException;
	
	public void saveEveryHour(String cityName) throws IOException;
	
	public void compareTemp(String cityName, Date startDate, String mode) throws IOException, DateTimeException, ParseException, DataFormatException; //oneCall
	
}
