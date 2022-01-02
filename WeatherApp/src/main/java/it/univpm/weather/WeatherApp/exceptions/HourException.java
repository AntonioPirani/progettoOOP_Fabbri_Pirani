package it.univpm.weather.WeatherApp.exceptions;

/**
 * 
 * @author Matteo Fabbri
 *
 */

public class HourException extends Exception
{
	private static final long serialVersionUID = 3L;
	String output;
	
	public HourException() 
	{
		super();
	}

	public HourException(String output) 
	{
		super();
		this.output = output;
	}

	public String getOutput() {
		return output;
	}
}
