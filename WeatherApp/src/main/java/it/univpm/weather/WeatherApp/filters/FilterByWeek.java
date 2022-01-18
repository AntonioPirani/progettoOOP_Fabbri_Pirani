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

import it.univpm.weather.WeatherApp.exceptions.HistoryException;
import it.univpm.weather.WeatherApp.exceptions.InvalidPeriodException;
import it.univpm.weather.WeatherApp.stats.Statistics;

/**
 * Classe che estende la classe Filter. Riguarda l'utilizzo del filtro secondo la base settimanale
 * 
 * @author Antonio Pirani
 * @author Matteo Fabbri
 *
 */
public class FilterByWeek extends Filter {

	/**
	 * Costruttore con parametri
	 * 
	 * @param cityName Nome della città richiesta
	 * @param time Numero di settimane passate rispetto alla data attuale
	 */
	public FilterByWeek(String cityName, int time) {
		super();
		this.filterBy = "week";
		this.cityName = cityName;
		this.time = time;
	}

	/**
	 * Metodo usato per calcolare le statistiche alle quali viene applicato il filtro settimanale.
	 * 
	 * @return Stringa mista contentente testo e JSON dei valori delle temperature reali e 
	 * 		percepite calcolati
	 * @throws InvalidPeriodException se il periodo inserito non è valido
	 * @throws HistoryException se lo storico non esiste
	 */
	public String calculate() throws InvalidPeriodException, HistoryException {
		
		stats = new Statistics(true);
		JSONArray filter = null;
		
		if (time < 1) throw new InvalidPeriodException("Non è stato inserito un periodo di tempo valido");
		
		filter = filter();
		if (filter == null || filter.size() == 0) throw new HistoryException(cityName);
	
		stats.statsCalc(filter);
		
		String response = "Temperatura reale: " + toJson().toString();
		
		stats = new Statistics(false);
		filter = filter();
		stats.statsCalc(filter);
		
		response = response + "<br>Temperatura percepita: " + toJson().toString();
		
		return response;
	}

	/**
	 * Metodo che si occupa della creazione e del riempimento del JSONArray che è necessario per 
	 * il metodo calculate(). I dati vengono prelevati dallo storico se rispettano la condizione
	 * di appartenere al periodo di tempo esatto.
	 * 
	 * @return JSONArray array con tutti i dati necessari al calcolo dei valori
	 */
	@SuppressWarnings("unchecked")
	public JSONArray filter() {
		
		String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "files" + System.getProperty("file.separator") + cityName + ".txt";
		File file = new File(filePath);
		
		try {
			
			if(!file.exists()) throw new FileNotFoundException();
				
		} catch (FileNotFoundException e) {

			System.out.println("Lo storico di " + cityName + " non esiste");
			return null;
		}
		
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
				
				if(((long) obj.get("dt") >= previousTime(time*24*7)) && ((long) obj.get("dt") <= Instant.now().getEpochSecond())) {		
					
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
