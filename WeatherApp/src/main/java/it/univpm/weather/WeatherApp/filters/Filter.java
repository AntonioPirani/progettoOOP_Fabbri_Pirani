package it.univpm.weather.WeatherApp.filters;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashMap;

import org.json.simple.JSONObject;

import it.univpm.weather.WeatherApp.stats.Statistics;

/**
 * Classe di Filtro delle statistiche, che comprende un oggetto di Statistics, il nome della città, 
 * il tipo di filtro applicato e il tempo passato specificato
 * 
 * @author Antonio Pirani
 *
 */
public class Filter {

	Statistics stats;
	String cityName;
	String filterBy;
	int time;
	
	/**
	 * Metodo che restituisce il contenuto della classe in formato JSON, con all'interno
	 * tutte le informazioni richieste dalle statistiche, con aggiunta del tipo di filtro e del
	 * valore di tempo passato inserito
	 * 
	 * @return JSONObject il json richiesto
	 */
	public JSONObject toJson() {
		
		JSONObject obj = new JSONObject();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		//map.put("cityName", stats.getCityName());
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
	
	/**
	 * Metodo che calcola il tempo in formato UNIX del numero di ore passate 
	 * inserite dall'utente rispetto alla data attuale 
	 * 
	 * @param d numero di ore passate
	 * @return data in formato UNIX corrispondente
	 */
	public long previousTime(int d) {
		
        long now = Instant.now().getEpochSecond();
        return now - d*3600;
        
    }
	
}
