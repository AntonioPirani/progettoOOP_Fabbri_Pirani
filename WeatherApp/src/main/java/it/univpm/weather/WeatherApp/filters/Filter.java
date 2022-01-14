package it.univpm.weather.WeatherApp.filters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashMap;

import org.json.simple.JSONObject;

import it.univpm.weather.WeatherApp.stats.Statistics;

/**
 * 
 * @author Antonio Pirani
 *
 */
public class Filter {

	Statistics stats;
	String cityName;
	String filterBy;
	int time;
	
	public JSONObject toJson() {
		
		JSONObject obj = new JSONObject();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("max", stats.getMax());
		map.put("min", stats.getMin());
		map.put("avg", BigDecimal.valueOf(stats.getAvg())
			    .setScale(3, RoundingMode.HALF_UP)
			    .doubleValue());
		map.put("var", BigDecimal.valueOf(stats.getVar())
			    .setScale(3, RoundingMode.HALF_UP)
			    .doubleValue());
		map.put("filter", filterBy);
		map.put("timeAgo", time);
		
		obj = new JSONObject(map);
		
		return obj;
		
	}
	
	//base oraria
	public long previousTime(int d) {
		
        long now = Instant.now().getEpochSecond();
        return now - d*3600;
        
    }
	
	
}
