package it.univpm.weather.WeatherApp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

	/** La classe City descrive le proprietà di ogni città.
	 * 
	 * @author Matteo Fabbri
	 * 
	 */
	public class City {
		
	private String cityName;
	private long cityId;
	private Coordinates coords;
	private Temperature currentTemp;
	private ArrayList<Temperature> temp;
	private int timeZone;
	
	/** Costruttore senza parametri.
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
	 * @param coords
	 * @param currentTemp
	 * @param temp
	 */
	public City(String cityName, long cityId, int timeZone, Coordinates coords, Temperature currentTemp,
			ArrayList<Temperature> temp) {
		super();
		this.cityName = cityName;
		this.cityId = cityId;
		this.coords = coords;
		this.currentTemp = currentTemp;
		this.temp = temp;
		this.timeZone = timeZone;
	}
	
	/** Costruttore con unicamente il parametro cityName.
	 * 
	 * @param cityName
	 */
	public City(String cityName) {
		super();
		this.cityName = cityName;
	}

	/** Metodo get che restituisce il nome della città.
	 * 
	 * @return cityName
	 */
	public String getCityName() 
	{
		return cityName;
	}

	/** Metodo set che imposta il nome della città.
	 * 
	 * @param cityName
	 */
	public void setCityName(String cityName) 
	{
		this.cityName = cityName;
	}

	/** Metodo get che restituisce l'ID della città.
	 * 
	 * @return cityId
	 */
	public long getCityId() 
	{
		return cityId;
	}

	/** Metodo set che imposta l'ID della città.
	 * 
	 * @param cityId
	 */
	public void setCityId(long cityId) 
	{
		this.cityId = cityId;
	}
	
	/** Metodo get che restituisce il fusorario.
	 * 
	 * @return timezone
	 */
	public int getTimeZone() {
		return timeZone;
	}

	/** Metodo set che imposta il fusorario.
	 * 
	 * @param timeZone
	 */
	public void setTimeZone(int timeZone) {
		this.timeZone = timeZone;
	}

	/** Metodo get che restituisce le coordinate della città.
	 * 
	 * @return coords
	 */
	public Coordinates getCoords() 
	{
		return coords;
	}

	/** Metodo set che imposta le coordinate della città.
	 * 
	 * @param coords
	 */
	public void setCoords(Coordinates coords) 
	{
		this.coords = coords;
	}
	
	/** Metodo get che restituisce i parametri riguardanti la temperatura della città.
	 * 
	 * @param coords
	 */
	public Temperature getCurrentTemp() {
		return currentTemp;
	}
	
	/** Metodo set che imposta i parametri riguardanti la temperatura della città.
	 * 
	 * @param coords
	 */
	public void setCurrentTemp(Temperature currentTemp) {
		this.currentTemp = currentTemp;
	}

	/** Metodo get che restituisce un ArrayList di oggetti di tipo Temperature.
	 * 
	 * @return temp
	 */
	public ArrayList<Temperature> getTemp() {
		return temp;
	}

	/** Metodo set che imposta un ArrayList di oggetti di tipo Temperature.
	 * 
	 * @param temp
	 */
	public void setTemp(ArrayList<Temperature> temp) {
		this.temp = temp;
	}
	
	/** Metodo che restituisce un oggetto di tipo JSONObject contente i dati realivi a cityName, cityId, timeZone, currentTemp e coords .
	 * 
	 * @return obj
	 */
	public JSONObject toJson() {
		
		JSONObject obj = new JSONObject();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("cityName", cityName);
		
		map.put("cityId", cityId);
		
		map.put("timeZone", timeZone);
		
		map.put("dateTime", currentTemp.getDateTime());

		map.put("temp", BigDecimal.valueOf(currentTemp.getTemp())
			    .setScale(3, RoundingMode.HALF_UP)
			    .doubleValue());
		map.put("feelsLike", BigDecimal.valueOf(currentTemp.getFeelsLike())
			    .setScale(3, RoundingMode.HALF_UP)
			    .doubleValue());
		
		map.put("lat", coords.getLat());
		map.put("lon", coords.getLon());

		obj = new JSONObject(map);
		
		return obj;
		
	}
}
