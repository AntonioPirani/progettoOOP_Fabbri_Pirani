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

	public JSONObject toJson() {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("exception", "HistoryException");
		map.put("mex", "Lo storico di " + txt + " non esiste");
		
		JSONObject obj = new JSONObject(map);
		
		return obj;
		
	}
	
}
