package it.univpm.weather.WeatherApp.exceptions;

/** La classe HourException gestice l'eccezzione quando l'intervallo orario non è valido.
 * 
 * @author Matteo Fabbri
 */

public class HourException extends Exception
{
	private static final long serialVersionUID = 3L;
	String output;
	
	/** Costruttore senza parametri.
	 */
	public HourException() 
	{
		super();
	}

	/** Costruttore con parametri.
	 * 
	 * @param output (Messaggio di errore)
	 */
	public HourException(String output) 
	{
		super();
		this.output = output;
	}

	/** Metodo get che restituisce la stringa di eccezione personalizzata. 
	 * 
	 * @return output (Messaggio di errore)
	 */
	public String getOutput() {
		return output;
	}
}
