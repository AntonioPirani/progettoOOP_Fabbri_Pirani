package it.univpm.weather.WeatherApp.exceptions;

/**
 * 
 * @author Matteo Fabbri
 *
 */

public class InvalidPeriodException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	String output;
	
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
	public String getOutput() 
	{
		return output;
	}
	
}
