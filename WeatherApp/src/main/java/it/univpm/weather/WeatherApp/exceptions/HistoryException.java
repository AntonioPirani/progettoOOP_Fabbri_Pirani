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

}
