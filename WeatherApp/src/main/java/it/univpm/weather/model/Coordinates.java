package it.univpm.weather.model;

import java.util.Objects;


/** La classe Coordinates descrive tutte le informazioni riguardanti le coordinate della citàà inserita.
 * @author Matteo
 */

public class Coordinates 
{
	private double lat;
	private double lon;
	
	/**
	 * @param lat
	 * @param lon
	 */
	public Coordinates(double lat, double lon) 
	{
		super();
		this.lat = lat;
		this.lon = lon;
	}

	/**
	 * @return lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return lon
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * @param lon
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "Coordinates [lat=" + lat + ", lon=" + lon + "]";
	}
	
}
