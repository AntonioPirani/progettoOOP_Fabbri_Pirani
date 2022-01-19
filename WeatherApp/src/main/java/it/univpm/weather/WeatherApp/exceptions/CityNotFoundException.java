package it.univpm.weather.WeatherApp.exceptions;

import java.util.HashMap;

import org.json.simple.JSONObject;

/** CityNotFoundExceotion è una eccezione personalizzata per la gestione dell'inserimento di una città non valida.
 * 
 * @author Matteo Fabbri
 */

public class CityNotFoundException extends Exception
{
	
	private static final long serialVersionUID = 1L;
	String cityName;
	String output;
	
	/** Costruttore senza parametri.
	 */
	public CityNotFoundException() 
	{
		super();
	}

	/** Costruttore con parametri.
	 * 
	 * @param output (Messaggio di errore)
	 * @param cityName Nome della città non trovata
	 */
	public CityNotFoundException(String output, String cityName) 
	{
		super();
		this.output = output;
		this.cityName = cityName;
	}

	/** Metodo get che restituisce la stringa di eccezione personalizzata. 
	 * 
	 * @return output (Messaggio di errore)
	 */
	public String getOutput() 
	{
		return output;
	}
	
	/**
	 * Metodo che permette di restituire le caratteristiche della eccezione in formato JSON, 
	 * ossia il nome della eccezione e il messaggio
	 * 
	 * @return JSONObject con le informazioni sopraccitate
	 */
	public JSONObject toJson() {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("exception", "CityNotFoundException");
		map.put("cityName", cityName);
		
		JSONObject obj = new JSONObject(map);
		
		return obj;
		
	}
}
