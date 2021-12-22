package it.univpm.weather.WeatherApp.model;

/** La classe City descrive le proprietà di ogni città.
 * 
 * @author Matteo Fabbri
 */
	public class City {
		
	private String cityName;
	private long cityId;
	private Coordinates coords;
	private Temperature temp;
	
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
	 * @param coords
	 * @param temp
	 */
	public City(String cityName, long cityId, Coordinates coords, Temperature temp) 
	{
		super();
		this.cityName = cityName;
		this.cityId = cityId;
		this.coords = coords;
		this.temp = temp;
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
	public Temperature getTemp() {
		return temp;
	}
	
	/** Metodo set che imposta i parametri riguardanti la temperatura della città.
	 * 
	 * @param coords
	 */
	public void setTemp(Temperature temp) {
		this.temp = temp;
	}

	/** Override del metodo toString.
	 * 
	 *@return Stringa di ritorno.
	 */
	@Override
	public String toString() {
		return "City [cityName=" + cityName + ", cityId=" + cityId + ", coords=" + coords + ", temp=" + temp + "]";
	}
	
}
