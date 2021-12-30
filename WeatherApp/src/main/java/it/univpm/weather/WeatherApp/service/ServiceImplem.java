package it.univpm.weather.WeatherApp.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.DataFormatException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;

import it.univpm.weather.WeatherApp.exceptions.CityNotFoundException;
import it.univpm.weather.WeatherApp.exceptions.HourException;
import it.univpm.weather.WeatherApp.model.*;

/** Classe che implementa l'interfaccia Service mettendo a disposizione i metodi richiamati dal controller
 * 
 * @author Antonio Pirani
 * @author Matteo Fabbri
 *
 */
@Service
public class ServiceImplem implements it.univpm.weather.WeatherApp.service.Service { 
	//richiamando solo Service lo scambia per una interfaccia predefinita di Spring
	//(rendendo necessaria l'implementazione di 2 metodi, stampando comunque un warning)
	
	/**
	 * {@value} apiKey chiave privata per l'accesso alle API
	 */
	private final String apiKey = "e75a0a03e0d0542a15e263930e56f99a";

	/**
	 * @param cityName stringa contenente il nome della città da cercare
	 * @return obj oggetto JSON con tutte le informazioni ottenute con la One Call API corrente
	 * @throws CityNotFoundException eccezione per quando la città inserita non viene trovata
	 * @throws IOException eccezione di input/output
	 * @throws ParseException se ci sono errori nel formato JSON
	 */
	public JSONObject getTemperature(String cityName) throws IOException {

		JSONParser parser = new JSONParser();
		Coordinates coords = new Coordinates();
		
		try {
			
			coords = getCityCoords(cityName);
			
		} catch (IOException | CityNotFoundException e) {
			
			e.printStackTrace();
		}

		String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + coords.getLat() + "&lon=" + coords.getLon() + "&exclude=hourly,daily,minutely,alerts&appid=" + apiKey +"&units=metric";
		
		InputStream input = new URL(url).openConnection().getInputStream();
		
		BufferedReader re = new BufferedReader(new InputStreamReader(input));  
	    
	    String text = read(re);  
	    String testo = (text.substring(0, text.length() - 1));
	    
	    JSONObject obj = null;
	    
	    try {
	    	
	    	obj = (JSONObject) parser.parse(testo);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
			
		} finally {
	    	
			input.close();
		      
		}
	    
	    HashMap<String,Object> map = new HashMap<String,Object>();
	    
	    map.put("name", cityName);
		map.put("lat", obj.get("lat"));
		map.put("lon", obj.get("lon"));
		
		obj = (JSONObject) obj.get("current");
		
		map.put("temp", obj.get("temp"));
		map.put("feels_like", obj.get("feels_like"));
		map.put("dt", obj.get("dt"));
		
		obj = new JSONObject(map);
	    
		return obj;
	}
	
	/** Metodo che permette di ottenere le coordinate per una specifica città, necessarie per la One Call API
	 * 
	 * @param cityName stringa contenente il nome della città da cercare
	 * @return classe coordinate: latitudine e longitudine relative alla città
	 * @throws IOException per errori di input/output
	 * @throws JsonParseException in caso di errori nel JSON
	 * @throws CityNotFoundException eccezione per quando non si trova la città desiderata
	 */
	public Coordinates getCityCoords(String cityName) throws IOException, JsonParseException, CityNotFoundException {
		
		JSONParser parser = new JSONParser();
		Coordinates coord;
		
		String url = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=1&appid=" + apiKey;
		
		InputStream input = new URL(url).openConnection().getInputStream();
		
		try {                
			
	      BufferedReader re = new BufferedReader(new InputStreamReader(input));  
	    
	      String text = read(re);  
	      String testo = (text.substring(0, text.length() - 1)); //per togliere ?
	      
	      if(testo.equals("[]")) {
	    	  throw new CityNotFoundException("La città " + cityName + " non è stata trovata");
	      }
	      
	      testo = (testo.substring(1, testo.length() - 1)); //per togliere { e }
	      
	      JSONObject obj = (JSONObject) parser.parse(testo);
	      
	      double lat = (double) obj.get("lat");
	      double lon = (double) obj.get("lon");
	      
	      coord = new Coordinates(lat, lon);

	      return coord;
	      
	    } catch (CityNotFoundException | ParseException e) {
	    
	      System.out.println("ERRORE");
	      System.out.println(e);
	      return coord = new Coordinates(0, 0);
	      
	    } finally {
	    	
	      input.close();
	      
	    }
	}

	/** Metodo che permette di salvare su file la temperatura corrente. Il nuovo file viene salvato nella cartella "files" con lo stesso nome della città ricercata
	 * 
	 * @param obj JSONObject che contiene tutte le informazioni principali da salvare su file
	 * @throws IOException in caso di problemi sul file
	 * @throws HourException 
	 */
	public boolean saveCurrentTemp(JSONObject obj) throws IOException, HourException {
		
		String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "files" + System.getProperty("file.separator") + obj.get("name") + ".txt";
		//System.out.println("percorso: " + filePath); //C:\Users\anton\git\progettoOOP_Fabbri_Pirani\WeatherApp\files\
		//https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html user.home no
		
		File file = new File(filePath);
		//Writer writer = null; non funziona l'append con Writer
		
		try {
			
			if(checkLastLineDate(file, (long) obj.get("dt"))) {
			
				throw new HourException("Differenza di orario inferiore ad 1 ora");
				
				//System.out.println("File non salvato");
				//return false;
				
			}
			
		} catch (HourException e){
			
			System.out.println("ERRORE saveCurrentTemp");
			System.out.println(e);
			return false;
			
		}
		
		BufferedWriter bufferedWriter = null;
		
		try  {
			
			bufferedWriter = new BufferedWriter(new FileWriter(file, true));
			bufferedWriter.write(obj.toString() + System.lineSeparator());
			
			System.out.println("Dati salvati in: " + filePath);
			
			//writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"));
			//writer.write(obj.toString());
			
			return true;
		
		} catch (IOException e) {
			
			System.out.println("ERRORE nel file");
			System.out.println(e);
			return false;
		    
		} finally {
			
		   //writer.close();
		   bufferedWriter.close();
		}
			
	}

	public void saveEveryHour(String cityName) throws IOException {
	
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			
		    @Override
		    public void run() {
		        
		    	try {
		    		
		    		JSONObject obj = getTemperature(cityName);
		    		
					if(saveCurrentTemp(obj)) { 
						
						System.out.println("Salvataggio riuscito");
						
					}
					
					else { 
						
						System.out.println("Salvataggio non riuscito - riprovare più tardi");
						
					}
					
				} catch (IOException | HourException e) {

					e.printStackTrace();
					
				}
		    }
		    
		}, 0, 1, TimeUnit.HOURS); //https://stackoverflow.com/questions/32228345/run-java-function-every-hour
		
	}

	public void compareTemp(String cityName, Date startDate, String mode)
			throws IOException, DateTimeException, ParseException, DataFormatException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	//metodi aggiuntivi
	
	/** Metodo che legge tutto in contenuto dell'url
	 * 
	 * @param re Lettore necessario alla lettura
	 * @return stringa contenente tutto il contenuto letto
	 * @throws IOException in caso di errore di input/output
	 */
	public String read(Reader re) throws IOException {   
		
	    StringBuilder str = new StringBuilder();     
	    int temp;
	    
	    do {

	      temp = re.read();       
	      str.append((char) temp);

	    } while (temp != -1);        
	    
	    //  https://www.delftstack.com/howto/java/java-get-json-from-url/
	    return str.toString();

	}
	
	public long previousHour(int d) {
		
        long now = Instant.now().getEpochSecond();
        return now - d*3600;
        
    }
	
	/** Metodo che controlla se la differenza tra il valore "dt" dell'ultima riga del file passato e il valore del valore "dt" preso dall'url è superiore a 1 ora
	 * 
	 * @param file il file da aprire
	 * @param urlDT il valore dt preso dall' url
	 * @param fileDT l'ultimo valore dt preso da file
	 * @return true se c'è l'errore
	 * @return false se la differenza è > 1 ora
	 */
	public boolean checkLastLineDate(File file, long urlDT) {
		
		JSONParser parser = new JSONParser();
		
		String read = "";
		 
		try ( Scanner x = new Scanner (file); ) {
			  
		  while (x.hasNextLine()) {
			  
			  read = x.nextLine() + System.lineSeparator(); 
			  
		  } 
		   
		  JSONObject obj = (JSONObject) parser.parse(read);
		   
		  long fileDT = (long) obj.get("dt");
		   
		  if( urlDT - fileDT < 3599) { // per correttezza do 1 secondo di distacco - 3600 non va bene
			  //System.out.println("ERRORE: differenza inferiore ad 1 ora");
			  return true; //true -> errore
		  }
		    
		} catch (Exception e) {
			
			System.out.println("Impossibile aprire il file - se non esiste verrà creato");
			
		}
		
		//System.out.println("OK: differenza superiore ad 1 ora");
		
		return false; //false -> corretto
		
	}

}
