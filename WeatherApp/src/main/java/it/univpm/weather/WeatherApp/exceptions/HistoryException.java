package it.univpm.weather.WeatherApp.exceptions;

/**
 * 
 * @author Antonio Pirani
 *
 */
public class HistoryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	
	String txt;

	public HistoryException(String txt) {
		super();
		this.txt = txt;
	}

	public String getTxt() {
		return txt;
	}

}
