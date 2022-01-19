package it.univpm.weather.WeatherApp.exceptions;

import java.util.HashMap;

import org.json.simple.JSONObject;

/** La classe InvalidPeriodException gestice l'eccezzione quando il periodo non è valido.
 * 
 * @author Matteo Fabbri
 */

public class InvalidPeriodException extends Exception
{
	private static final long serialVersionUID = 2L;
	String output;
	
	/** Costruttore senza parametri.
	 */
	public InvalidPeriodException()
	{
		super();
	}

	/** Costruttore con parametri.
	 * 
	 * @param output (Messaggio di errore)
	 */
	public InvalidPeriodException (String output) 
	{
		super();
		this.output = output;
	}

	/** Metodo get che restituisce la stringa di eccezione personalizzata. 
	 * 
	 * @return output (Messaggio di errore)
	 */
	public String getMessage() 
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
		
		map.put("exception", "InvalidPeriodException");
		map.put("mex", output);
		
		JSONObject obj = new JSONObject(map);
		
		return obj;
		
	}
	
}
