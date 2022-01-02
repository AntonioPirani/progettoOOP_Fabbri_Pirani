package it.univpm.weather.WeatherApp.service;

import it.univpm.weather.WeatherApp.exceptions.*;
import it.univpm.weather.WeatherApp.model.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;
/**
 * 
 * @author Antonio Pirani
 *
 */

public interface Service {
	
	public JSONObject getTemperature(String cityName) throws IOException; //current
	
	public JSONArray timeMachine(String cityName, long dt) throws IOException;
	
	public Coordinates getCityCoords(String cityName) throws IOException, JsonParseException, CityNotFoundException; //oneCall
	
	public boolean saveCurrentTemp(JSONObject obj) throws IOException, HourException;
	
	public void saveEveryHour(String cityName) throws IOException;
	
	public String compareTemp(String cityName, int prevDay) throws IOException, ParseException; //oneCall
	
}
