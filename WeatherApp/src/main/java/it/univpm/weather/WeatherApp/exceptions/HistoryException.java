package it.univpm.weather.WeatherApp.exceptions;

import java.util.HashMap;

import org.json.simple.JSONObject;

/**Eccezione che gestisce il caso in cui lo storico richiesto per le statistiche non esiste
 * 
 * @author Antonio Pirani
 *
 */
public class HistoryException extends Exception {

	private static final long serialVersionUID = 4L;
	
	String txt;	//cityName

	/**
	 * 
	 * Costruttore con parametro
	 * 
	 * @param txt
	 */
	public HistoryException(String txt) {
		super();
		this.txt = txt;
	}

	/**
	 * Metodo che restituisce il messaggio
	 */
	public String getMessage() {
		return txt;
	}

	/**
	 * Metodo che permette di restituire le caratteristiche della eccezione in formato JSON, 
	 * ossia il nome della eccezione e il messaggio
	 * 
	 * @return JSONObject con le informazioni sopraccitate
	 */
	public JSONObject toJson() {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("exception", "HistoryException");
		map.put("mex", txt);
		
		JSONObject obj = new JSONObject(map);
		
		return obj;
		
	}
	
}
