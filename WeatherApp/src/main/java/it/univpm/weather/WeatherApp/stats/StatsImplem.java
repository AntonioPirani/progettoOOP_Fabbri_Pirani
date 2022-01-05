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

public class StatsImplem implements StatsInterface {
	
	public Statistics getStats(String cityName, boolean b) {
		
		String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "files" + System.getProperty("file.separator") + cityName + ".txt";
		
		File file = new File(filePath);
			
		try {
			
			if(!file.exists()) {

				throw new FileNotFoundException();
				
			}
			
		} catch (FileNotFoundException e) {

			System.out.println("Lo storico di " + cityName + " non esiste");
			e.printStackTrace();
			
			return null;
		}

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
			
			System.out.println("Eccezione calcolo statistiche");
			e.printStackTrace();
			
			return null;
			
		}
		
		return stats;
		
	}
	
	@SuppressWarnings("unchecked") // per il json simple, si poteva evitare anche la HashMap
	public JSONArray createArray(File file, boolean b) {
		
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
			
			e.printStackTrace();
			return array = null;
			
		} finally {
			
			x.close();
			
		}

		return array;
		
	}

}
