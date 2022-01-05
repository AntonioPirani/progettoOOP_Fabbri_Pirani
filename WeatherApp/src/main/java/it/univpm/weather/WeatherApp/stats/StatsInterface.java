package it.univpm.weather.WeatherApp.stats;

import java.io.File;

import org.json.simple.JSONArray;

public interface StatsInterface {
	
	public Statistics getStats(String cityName, boolean b);
	
	public JSONArray createArray(File file, boolean b);

}
