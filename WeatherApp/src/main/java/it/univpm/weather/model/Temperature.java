package it.univpm.weather.model;

import java.util.Objects;

/** La classe Temperature descrive tutte le informazioni riguardanti temperatura percepita e correnti.
 * @author Matteo
 */
public class Temperature 
{
	
	private long dateTime;
	private double feelsLike;
	private double currentTemp;
	private double maxTemp;
	private double minTemp;
	
	/**
	 * Costruttore dell'oggetto.
	 */
	public Temperature() 
	{
		super();
	}
	
	/** Costruttore con tutti i parametri.
	 * 
	 * @param dateTime
	 * @param feelsLike
	 * @param currentTemp
	 * @param maxTemp
	 * @param minTemp
	 */
	public Temperature(long dateTime, double feelsLike, double currentTemp, double maxTemp, double minTemp) 
	{
		super();
		this.dateTime = dateTime;
		this.feelsLike = feelsLike;
		this.currentTemp = currentTemp;
		this.maxTemp = maxTemp;
		this.minTemp = minTemp;
	}
	
	/** Metodo get che restituisce il momento (in formato UNIX) preso in considerazione per l'analisi della temperatura della città.
	 * 
	 * @return dateTime
	 */
	public long getDateTime() 
	{
		return dateTime;
	}
	
	/** Metodo set che imposta il momento (in formato UNIX) preso in considerazione per l'analisi della temperatura della città.
	 * 
	 * @param dateTime
	 */
	public void setDateTime(long dateTime) 
	{
		this.dateTime = dateTime;
	}

	/** Metodo get che restituisce la temperatura percepita.
	 * 
	 * @return feelsLike
	 */
	public double getFeelsLike() 
	{
		return feelsLike;
	}

	/** Metodo set che imposta la temperatura percepita.
	 * 
	 * @param feelsLike
	 */
	public void setFeelsLike(double feelsLike) 
	{
		this.feelsLike = feelsLike;
	}

	/** Metodo get che restituisce la temperatura attuale.
	 * 
	 * @return correntTemp
	 */
	public double getCurrentTemp() 
	{
		return currentTemp;
	}

	/** Metodo set che imposta la temperatura attuale.
	 * 
	 * @param currentTemp
	 */
	public void setCurrentTemp(double currentTemp) 
	{
		this.currentTemp = currentTemp;
	}

	/** Metodo get che restituisce la temperatura massima. 
	 * 
	 * @return maxTemp
	 */
	public double getMaxTemp() 
	{
		return maxTemp;
	}

	/** Metodo set che imposta la temperatura massima. 
	 * 
	 * @param maxTemp
	 */
	public void setMaxTemp(double maxTemp) 
	{
		this.maxTemp = maxTemp;
	}

	/** Metodo get che restituisce la temperatura minima. 
	 * 
	 * @return minTemp
	 */
	public double getMinTemp()
	{
		return minTemp;
	}

	/** Metodo set che imposta la temperatura minima. 
	 * 
	 * @param minTemp
	 */
	public void setMinTemp(double minTemp) 
	{
		this.minTemp = minTemp;
	}

	/** Override del metodo toString.
	 * 
	 *@return Stringa di ritorno.
	 */
	@Override
	public String toString() {
		return "Temperature [dateTime=" + dateTime + ", feelsLike=" + feelsLike + ", currentTemp=" + currentTemp
				+ ", maxTemp=" + maxTemp + ", minTemp=" + minTemp + "]";
	}
	
}
