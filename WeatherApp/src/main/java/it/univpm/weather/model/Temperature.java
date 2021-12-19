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
	
	/** 
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
	
	/**
	 * @return dateTime
	 */
	public long getDateTime() 
	{
		return dateTime;
	}
	
	/**
	 * @param dateTime
	 */
	public void setDateTime(long dateTime) 
	{
		this.dateTime = dateTime;
	}

	/**
	 * @return feelsLike
	 */
	public double getFeelsLike() 
	{
		return feelsLike;
	}

	/**
	 * @param feelsLike
	 */
	public void setFeelsLike(double feelsLike) 
	{
		this.feelsLike = feelsLike;
	}

	/**
	 * @return correntTemp
	 */
	public double getCurrentTemp() 
	{
		return currentTemp;
	}

	/**
	 * @param currentTemp
	 */
	public void setCurrentTemp(double currentTemp) 
	{
		this.currentTemp = currentTemp;
	}

	/**
	 * @return maxTemp
	 */
	public double getMaxTemp() 
	{
		return maxTemp;
	}

	/**
	 * @param maxTemp
	 */
	public void setMaxTemp(double maxTemp) 
	{
		this.maxTemp = maxTemp;
	}

	/**
	 * @return minTemp
	 */
	public double getMinTemp()
	{
		return minTemp;
	}

	/**
	 * @param minTemp
	 */
	public void setMinTemp(double minTemp) 
	{
		this.minTemp = minTemp;
	}

	@Override
	public String toString() {
		return "Temperature [dateTime=" + dateTime + ", feelsLike=" + feelsLike + ", currentTemp=" + currentTemp
				+ ", maxTemp=" + maxTemp + ", minTemp=" + minTemp + "]";
	}
	
}
