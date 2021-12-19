package it.univpm.weather.service;

//import it.univpm.weather.exceptions.CityNotFound; TODO
import it.univpm.weather.model.*; 

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
	
	public Coordinates getCityCoords(String cityName) throws IOException, JsonParseException; //oneCall
	
	public void saveCurrentTemp(String cityName) throws IOException;
	
	public void saveEveryHour(String cityName) throws IOException;
	
	public void compareTemp(String cityName, Date startDate, String mode) throws IOException, DateTimeException, ParseException, DataFormatException; //oneCall
	
	
	
}
