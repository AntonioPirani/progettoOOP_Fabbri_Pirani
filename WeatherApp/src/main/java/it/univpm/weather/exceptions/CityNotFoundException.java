package it.univpm.weather.exceptions;

/**
 * 
 * @author Matteo Fabbri
 *
 */

public class CityNotFoundException extends Exception
{
	String output;

	/** Costruttore con parametri.
	 * 
	 * @param output
	 */
	public CityNotFoundException(String output) 
	{
		super();
		this.output = output;
	}

	/** Metodo get che restituisce la stringa di eccezione personalizzata. 
	 * 
	 * @return output
	 */
	public String getOutput() 
	{
		return output;
	}
	
}
