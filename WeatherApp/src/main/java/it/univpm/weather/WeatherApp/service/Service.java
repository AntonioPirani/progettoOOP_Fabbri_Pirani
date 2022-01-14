package it.univpm.weather.WeatherApp.service;

import it.univpm.weather.WeatherApp.exceptions.*;
import it.univpm.weather.WeatherApp.model.*;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Interfaccia che contiene i principali metodi utilizzati nel progetto, usati per ottenere i dati relativi
 * alle temperature reali e percepite, attuali e passate, mediante chiamata alle API di OpenWeather. Tra le 
 * operazioni possibili vi è inoltre la possibilità di salvare i dati con cadenza oraria, confrontare le 
 * temperature di tot giorni passati con chiamate alle API
 * 
 * @author Antonio Pirani
 *
 */

public interface Service {
	
	public City getTemperature(String cityName) throws IOException, CityNotFoundException; //current
	
	public JSONArray timeMachine(String cityName, long dt) throws IOException;
	
	public City getCityCoords(String cityName) throws IOException, CityNotFoundException; //oneCall
	
	public boolean saveCurrentTemp(City city) throws IOException, HourException;
	
	public void saveEveryHour(String cityName) throws IOException;
	
	public String compareTemp(String cityName, int prevDay) throws IOException, ParseException, CityNotFoundException; //oneCall
	
}
