package it.univpm.weather.WeatherApp.exceptions;

/**Eccezione che gestisce il caso in cui lo storico richiesto per le statistiche non esiste
 * 
 * @author Antonio Pirani
 *
 */
public class HistoryException extends Exception {

	private static final long serialVersionUID = 4L;
	
	String txt;

	/**
	 * 
	 * @param txt
	 */
	public HistoryException(String txt) {
		super();
		this.txt = txt;
	}

	public String getMessage() {
		return txt;
	}

}
