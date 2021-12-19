package it.univpm.weather.model;

import java.util.Objects;

/** La classe City descrive le proprietà di ogni città.
 * @author Matteo Fabbri
 */
	public class City {
		
	private String cityName;
	private long cityId;
	private Coordinates coords;
	
	/** Costruttore dell'oggetto.
	 * 
	 */
	public City() 
	{
		super();
	}
	
	/** Costruttore con tutti i parametri.
	 * 
	 * @param cityName
	 * @param cityId
	 * @param dateTime
	 * @param feelsLike
	 * @param currentTemp
	 * @param coords
	 */
	public City(String cityName, long cityId, long dateTime, double feelsLike, double currentTemp,
			Coordinates coords) 
	{
		super();
		this.cityName = cityName;
		this.cityId = cityId;
		this.coords = coords;
	}

	/**
	 * @return cityName
	 */
	public String getCityName() 
	{
		return cityName;
	}

	/**
	 * @param cityName
	 */
	public void setCityName(String cityName) 
	{
		this.cityName = cityName;
	}

	/**
	 * @return cityId
	 */
	public long getCityId() 
	{
		return cityId;
	}

	/**
	 * @param cityId
	 */
	public void setCityId(long cityId) 
	{
		this.cityId = cityId;
	}

	/**
	 * @return coords
	 */
	public Coordinates getCoords() 
	{
		return coords;
	}

	/**
	 * @param coords
	 */
	public void setCoords(Coordinates coords) 
	{
		this.coords = coords;
	}

	@Override
	public String toString() {
		return "City [cityName=" + cityName + ", cityId=" + cityId + ", coords=" + coords + "]";
	}
	
}
