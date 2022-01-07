package it.univpm.weather.WeatherApp.stats;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;

public class Statistics {
	
	private double max = 0;
	private double min = 0;
	private double avg = 0;
	private double var = 0;
	private boolean bool;
	
	public Statistics() {
		super();
	}
	
	public Statistics(boolean b) {
		this.bool = b;
	}
	
	public Statistics(double max, double min, double avg, double var) {
		
		super();
		this.max = max;
		this.min = min;
		this.avg = avg;
		this.var = var;
		
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
	
	public String toString() {
		return "Statistics [max=" + max + ", min=" + min + ", avg=" + avg + ", var=" + var + "]";
	}

	private static double doubleValue(Object value) {
	    return (value instanceof Number ? ((Number)value).doubleValue() : -1.0);
	}
	
}
