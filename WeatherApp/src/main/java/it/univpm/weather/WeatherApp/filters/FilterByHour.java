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

import it.univpm.weather.WeatherApp.stats.*;

public class FilterByHour extends Filter {

	public FilterByHour(String cityName, int time) {
		super();
		this.filterBy = "hour";
		this.cityName = cityName;
		this.time = time;
	}
	
	public String calculate(boolean b) {
		
		stats = new Statistics(b);
		JSONArray filter = filter();
		
		if (filter == null || filter.size() == 0) return "Storico vuoto"; //TODO controllare
		else stats.statsCalc(filter);
		
		return toJson().toString();
		//TODO due istanze per reale e percepita
	}

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
				
				if((long) obj.get("dt") < previousTime(time)) {		//TODO
					System.out.println("Stop");
					return array;
				}
				
				System.out.println("data file: " + obj.get("dt") + "\n data calcolata: " + (previousTime(time)));
				
				HashMap<String,Object> map = new HashMap<String,Object>();
				
				map.put("dt", obj.get("dt"));
				
				if(stats.getBool()) map.put("temp", obj.get("temp"));
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
