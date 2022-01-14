package it.univpm.weather.WeatherApp.stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Classe che implementa l'interfaccia StatsInterface, definendo i metodi necessari
 * al funzionamento del programma
 * 
 * @author Antonio Pirani
 * @author Matteo Fabbri
 *
 */
public class StatsImplem implements StatsInterface {
	
	/**
	 * Metodo che restituisce un oggetto di tipo Statistics con tutte le informazioni relative
	 * alle statistiche calcolate
	 * 
	 * @param cityName nome della città di cui si vogliono analizzare le statistiche
	 * @param b booleano che indentifica se si tratta di temperatura reale o percepita
	 * @return oggetto Statistics con le informazioni relative a valore massimo, minimo, 
	 * 		medio e varianza
	 * @throws FileNotFoundException eccezione che indica che lo storico della città passata non esiste
	 * @throws ParseException eccezione che indica che c'è stato un errore nel parsing dello storico
	 * 
	 */
	public Statistics getStats(String cityName, boolean b) throws FileNotFoundException, ParseException {
		
		String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "files" + System.getProperty("file.separator") + cityName + ".txt";
		
		File file = new File(filePath);
			
		if(!file.exists()) throw new FileNotFoundException();	

		JSONArray array;
		Statistics stats;
		
		if (b) {
			
			array = createArray(file, true);
			stats = new Statistics(true);
		
		}
		
		else {
			
			array = createArray(file, false);
			stats = new Statistics(false);
			
		}
		
		try {
			
			stats.statsCalc(array);
			
		} catch (Exception e) {
			
			System.out.println(e);
			return null;
			
		}
		
		return stats;
		
	}
	
	/**
	 * Metodo che si occupa di creare il JSONArray che verrà usato per calcolare le statistiche
	 * 
	 * @param file File da cui verranno lette le informazioni e tradotte in array (storico)
	 * @param b booleano per indicare se si tratta di temperatura reale o corrente
	 * @return array JSONArray con tutte le informazioni da calcolare
	 * @throws ParseException eccezione per errori di parsing del file
	 * 
	 */
	@SuppressWarnings("unchecked") // per il json simple, si poteva evitare anche la HashMap
	public JSONArray createArray(File file, boolean b) throws ParseException {
		
		JSONArray array = new JSONArray();
		JSONParser parser = new JSONParser();
		
		String read = "";
		Scanner x = null;
		
		JSONObject obj = null;
		
		try {
			
			x = new Scanner (file);
			
			while (x.hasNextLine()) {
							  
				read = x.nextLine() + System.lineSeparator(); 
				
				obj = (JSONObject) parser.parse(read);

				HashMap<String,Object> map = new HashMap<String,Object>();
				
				map.put("dt", obj.get("dt"));
				
				if(b) map.put("temp", obj.get("temp"));
				else map.put("feels_like", obj.get("feels_like"));
				
				obj = new JSONObject(map);
				
				array.add(obj);
				
			} 
			
		} catch (ParseException | IOException e) {
			
			throw new ParseException(0);
			
		} finally {
			
			x.close();
			
		}

		return array;
		
	}

}
