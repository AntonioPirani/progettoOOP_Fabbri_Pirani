package it.univpm.weather.WeatherApp.stats;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import it.univpm.weather.WeatherApp.exceptions.HistoryException;

/**
 * Interfaccia per la gestione delle statistiche delle temperature attuali
 * 
 * @author Antonio Pirani
 * @author Matteo Fabbri
 *
 */
public interface StatsInterface {
	
	public Statistics getStats(String cityName, boolean b) throws ParseException, HistoryException;
	
	public JSONArray createArray(File file, boolean b) throws ParseException;

}
