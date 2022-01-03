package it.univpm.weather.WeatherApp.stats;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.Iterator;

public class Statistics {
	
	private double max = 0;
	private double min = 0;
	private double avg = 0;
	private double var = 0;
	
	public Statistics() {
		super();
	}

	public Statistics(double max, double min, double avg, double var) {
		
		super();
		this.max = max;
		this.min = min;
		this.avg = avg;
		this.var = var;
		
	}

	public void statsCalc(JSONArray array) {
		
		JSONObject obj = new JSONObject();
		Iterator<?> i = array.iterator();
		
		double sum = 0;
		
		while(i.hasNext()) {
			
			obj = (JSONObject) i.next(); 
			double temp = doubleValue(obj.get("temp"));
			
			max = (temp > max) ? temp : max;
			min = (temp < min) ? temp : min;
			
			sum += temp;
			
		}
		
		avg = sum / array.size();
		
		i = array.iterator();
		
		while(i.hasNext()) {
			
			obj = (JSONObject) i.next(); 
			double temp = doubleValue(obj.get("temp"));
			
			var += Math.pow((temp - avg), 2);
			
		}
		
		var = var / array.size();
		
	}
	
	public String toString() {
		return "Statistics [max=" + max + ", min=" + min + ", avg=" + avg + ", var=" + var + "]";
	}

	private static double doubleValue(Object value) {
	    return (value instanceof Number ? ((Number)value).doubleValue() : -1.0);
	}
	
}
