package it.univpm.weather.WeatherApp.filters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import it.univpm.weather.WeatherApp.exceptions.*;

import it.univpm.weather.WeatherApp.stats.*;

/**
 * Classe che estende la classe Filter. Riguarda l'utilizzo del filtro secondo la base oraria
 * 
 * @author Antonio Pirani
 * @author Matteo Fabbri
 *
 */
public class FilterByHour extends Filter {

	/**
	 * Costruttore con parametri
	 * 
	 * @param cityName Nome della città richiesta
	 * @param time Numero di ore passate rispetto alla data attuale
	 */
	public FilterByHour(String cityName, int time) {
		super();
		this.filterBy = "hour";
		this.cityName = cityName;
		this.time = time;
	}
	
	/**
	 * Metodo usato per calcolare le statistiche alle quali viene applicato il filtro orario.
	 * 
	 * @return Stringa mista contentente testo e JSON dei valori delle temperature reali e 
	 * 		percepite calcolati
	 * @throws InvalidPeriodException se il periodo inserito non è valido
	 * @throws HistoryException se lo storico non esiste
	 */
	public JSONObject calculate() throws InvalidPeriodException, HistoryException {
		
		stats = new Statistics(true);
		JSONArray filter = null;
		
		if (time < 1) throw new InvalidPeriodException("Non è stato inserito un periodo di tempo valido");	
		
		try {
			filter = filter();
		} catch (FileNotFoundException e) {	
			throw new HistoryException("Lo storico di " + cityName + " non esiste");	
		}
		
		if (filter == null || filter.size() == 0) throw new HistoryException("Lo storico non ha riportato dati relativi al periodo inserito");
	
		stats.statsCalc(filter);
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("cityName", cityName);
		
		map.put("temp", toJson());
		
		stats = new Statistics(false);
		
		try {
			filter = filter();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		stats.statsCalc(filter);
		map.put("feels_like", toJson());
		
		JSONObject obj = new JSONObject(map);
		
		return obj;
	}

	/**
	 * Metodo che si occupa della creazione e del riempimento del JSONArray che è necessario per 
	 * il metodo calculate(). I dati vengono prelevati dallo storico se rispettano la condizione
	 * di appartenere al periodo di tempo esatto.
	 * 
	 * @return JSONArray array con tutti i dati necessari al calcolo dei valori
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public JSONArray filter() throws FileNotFoundException {
		
		String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "files" + System.getProperty("file.separator") + cityName + ".txt";
		File file = new File(filePath);
		
		if(!file.exists()) throw new FileNotFoundException();
		
		JSONParser parser = new JSONParser();
		JSONArray array = new JSONArray();
		
		String read = "";
		Scanner x = null;
		
		JSONObject obj = null;
		
		try {
			
			x = new Scanner (file);
			
			while (x.hasNextLine()) {
							  
				read = x.nextLine() + System.lineSeparator(); 
				
				obj = (JSONObject) parser.parse(read);
				
				if(((long) obj.get("dt") >= previousTime(time)) && ((long) obj.get("dt") <= Instant.now().getEpochSecond())) {
					
					HashMap<String,Object> map = new HashMap<String,Object>();
				
					map.put("dt", obj.get("dt"));
					
					if(stats.getBool()) map.put("temp", obj.get("temp"));
					else map.put("feels_like", obj.get("feels_like"));
					
					obj = new JSONObject(map);
					
					array.add(obj);
				}
				
			} 
			
		} catch (ParseException | IOException e) {
			
			e.printStackTrace();
			return array = null;
			
		} finally {
			
			x.close();
			
		}
		
		return array;
	}

}
