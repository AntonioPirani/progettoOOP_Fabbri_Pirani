package it.univpm.weather.WeatherApp.stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StatsImplem implements StatsInterface {
	
	public void getStats(String cityName) {
		
		String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "files" + System.getProperty("file.separator") + cityName + ".txt";
		
		File file = new File(filePath);
			
		try {
			
			if(!file.exists()) {
				//TODO HistoryException
				throw new FileNotFoundException();
			}
			
		} catch (FileNotFoundException e) {

			System.out.println("Lo storico di " + cityName + " non esiste");
			e.printStackTrace();
			
		}
		
		JSONArray array = createArray(file);
		
		Statistics stats = new Statistics();
		stats.statsCalc(array);
		
	}
	
	public JSONArray createArray(File file) {
		
		JSONParser parser = new JSONParser();
		
		String read = "";
		Scanner x = null;
		
		try {
			
			x = new Scanner (file);
			
			while (x.hasNextLine()) {
							  
				read = x.nextLine();// + System.lineSeparator(); 
				
				JSONObject obj = (JSONObject) parser.parse(read);
				
			} 
			
		} catch (ParseException | IOException e) {
			
			e.printStackTrace();
			
		} finally {
			
			x.close();
			
		}
		
		
		return null;
		
	}

}
