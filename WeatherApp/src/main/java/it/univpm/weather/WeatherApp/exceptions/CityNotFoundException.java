package it.univpm.weather.WeatherApp.exceptions;

/** La classe CityNotFoundExceotion gestice l'eccezione quando una città non è valida.
 * 
 * @author Matteo Fabbri
 */

public class CityNotFoundException extends Exception
{
	
	private static final long serialVersionUID = 1L;
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
