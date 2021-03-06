package it.univpm.weather.WeatherApp.model;

/** La classe Coordinates descrive tutte le informazioni riguardanti le coordinate della citàà inserita.
 * 
 * @author Matteo Fabbri
 */

public class Coordinates 
{
	private double lat;
	private double lon;
	
	/** Costruttore dell'oggetto.
	 */
	public Coordinates() {
		super();
	}

	/** Costruttore con tutti i parametri.
	 * 
	 * @param lat
	 * @param lon
	 */
	public Coordinates(double lat, double lon) 
	{
		super();
		this.lat = lat;
		this.lon = lon;
	}

	/** Metodo get che restituisce la latitudine della città.
	 * 
	 * @return lat
	 */
	public double getLat() {
		return lat;
	}

	/** Metodo set che imposta la latitudine della città.
	 * 
	 * @param lat
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/** Metodo get che restituisce la longitudine della città.
	 * 
	 * @return lon
	 */
	public double getLon() {
		return lon;
	}

	/**Metodo set che imposta la longitudine della città.
	 * 
	 * @param lon
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}
}
