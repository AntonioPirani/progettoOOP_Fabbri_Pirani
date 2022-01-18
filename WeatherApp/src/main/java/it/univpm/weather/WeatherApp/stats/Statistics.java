package it.univpm.weather.WeatherApp.stats;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Classe che rappresenta le statistiche di una determinata città
 * 
 * @author Antonio Pirani
 *
 */
public class Statistics {
	
	private String cityName;
	private double max = 0;
	private double min = 0;
	private double avg = 0;
	private double var = 0;
	private boolean bool;
	
	/**
	 * Costruttore della classe
	 */
	public Statistics() {
		super();
	}
	
	/**
	 * Costruttore per lo specifico parametro booleano per differenziare tra temperatura reale
	 * e percepita
	 * 
	 * @param b true -> temperature reale, false -> temperatura percepita 
	 */
	public Statistics(boolean b) {
		this.bool = b;
	}
	
	/**
	 * Costruttore con parametri principali
	 * 
	 * @param max Valore massimo
	 * @param min Valore minimo
	 * @param avg Valore medio
	 * @param var Varianza
	 */
	public Statistics(double max, double min, double avg, double var, String cityName) {
		super();
		this.max = max;
		this.min = min;
		this.avg = avg;
		this.var = var;
		this.cityName = cityName;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getVar() {
		return var;
	}

	public void setVar(double var) {
		this.var = var;
	}
	
	public boolean getBool() {
		return bool;
	}

	/**
	 * Metodo che calcola e setta i parametri della classe
	 * 
	 * @param array JSONArray contenente le informazioni da calcolare
	 */
	public void statsCalc(JSONArray array) {
		
		JSONObject obj = new JSONObject();
		Iterator<?> i = array.iterator();
		
		double t = 0;
		double sum = 0;
		
		while(i.hasNext()) {
			
			obj = (JSONObject) i.next(); 
			
			double temp;
			
			if (bool) temp = doubleValue(obj.get("temp")); 
			else temp = doubleValue(obj.get("feels_like")); 
			
			max = (temp > t) ? temp : t;
			min = (temp < t) ? temp : t;
			
			sum += temp;
			t = temp;
		}
		
		avg = sum / array.size();
		
		i = array.iterator();
		
		while(i.hasNext()) {
			
			obj = (JSONObject) i.next(); 
			
			double temp;
			
			if (bool) temp = doubleValue(obj.get("temp")); 
			else temp = doubleValue(obj.get("feels_like")); 
			
			var += Math.pow((temp - avg), 2);
			
		}
		
		var = var / array.size();
		
	}
	
	/**
	 * Metodo che costruisce il JSONObject della classe con le principali informazioni per
	 * le statistiche. I valori di media e varianza vengono arrotondati alla terza cifra decimale
	 * 
	 * @return JSONObject richiesto
	 */
	public JSONObject toJson() {
		
		JSONObject obj = new JSONObject();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("max", max);
		map.put("min", min);
		map.put("avg", BigDecimal.valueOf(avg)
			    .setScale(3, RoundingMode.HALF_UP)
			    .doubleValue());
		map.put("var", BigDecimal.valueOf(var)
			    .setScale(3, RoundingMode.HALF_UP)
			    .doubleValue());
		
		obj = new JSONObject(map);
		
		return obj;
		
	}
	
	/**
	 * Metodo toString
	 */
	@Override
	public String toString() {
		return "Statistics [cityName=" + cityName + ", max=" + max + ", min=" + min + ", avg=" + avg + ", var=" + var
				+ "]";
	}

	/**
	 * Metodo analogo a quello presente su ServiceImplem. Necessario alla correzione del tipo
	 * restituito dal JSONObject
	 * 
	 * @param value Oggetto da trasformare in double
	 * @return valore corretto
	 */
	private static double doubleValue(Object value) {
	    return (value instanceof Number ? ((Number)value).doubleValue() : -1.0);
	}
	
}
