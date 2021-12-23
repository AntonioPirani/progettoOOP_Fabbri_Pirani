package it.univpm.weather.WeatherApp.exceptions;

/**
 * 
 * @author Matteo Fabbri
 *
 */

public class CityNotFoundException extends Exception
{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	String output;

	/** Costruttore con parametri.
	 * 
	 * @param output (Messaggio di errore)
	 */
	public CityNotFoundException(String output) 
	{
		super();
		this.output = output;
	}

	/** Metodo get che restituisce la stringa di eccezione personalizzata. 
	 * 
	 * @return output (Messaggio di errore)
	 */
	public String getOutput() 
	{
		return output;
	}
	
}
