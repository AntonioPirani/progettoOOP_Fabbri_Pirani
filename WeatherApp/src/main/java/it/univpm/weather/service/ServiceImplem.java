package it.univpm.weather.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.DateTimeException;
import java.util.Date;
import java.util.zip.DataFormatException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;

import it.univpm.weather.model.*;

@Service
public class ServiceImplem implements Service {
	
	private final String apiKey = "e75a0a03e0d0542a15e263930e56f99a";

	public JSONObject getTemperature(String cityName) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public Coordinates getCityCoords(String cityName) throws IOException, JsonParseException {
		
		Coordinates coord;
		JSONParser parser = new JSONParser();
		
		String url = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=1&appid=" + apiKey;
		
		InputStream input = new URL(url).openStream();
		
		try {                                
		      BufferedReader re = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));  
		    
		      String text = Read(re);         
		      JSONObject obj = (JSONObject) parser.parse(text);
		      
		      double lat = (double) obj.get("lat");
		      double lon = (double) obj.get("lon");
		      
		      coord = new Coordinates(lat, lon);
		      
		    } catch (Exception e) {
		    	
		      System.out.println(e);
		      return null;
		      
		    } finally {
		    	
		      input.close();
		      
		    }
		
		
		return coord;
	}
	
	public String Read(Reader re) throws IOException {   
		
	    StringBuilder str = new StringBuilder();     
	    int temp;
	    
	    do {

	      temp = re.read();       
	      str.append((char) temp);

	    } while (temp != -1);        
	    //  https://www.delftstack.com/howto/java/java-get-json-from-url/

	    return str.toString();

	}

	public void saveCurrentTemp(String cityName) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void saveEveryHour(String cityName) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void compareTemp(String cityName, Date startDate, String mode)
			throws IOException, DateTimeException, ParseException, DataFormatException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String value() {
		// TODO Auto-generated method stub
		return null;
	}

}
