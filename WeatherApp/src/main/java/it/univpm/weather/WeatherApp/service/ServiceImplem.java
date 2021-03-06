package it.univpm.weather.WeatherApp.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import it.univpm.weather.WeatherApp.exceptions.CityNotFoundException;
import it.univpm.weather.WeatherApp.exceptions.HourException;
import it.univpm.weather.WeatherApp.model.*;

/**
 * Classe che implementa l'interfaccia Service mettendo a disposizione i metodi richiamati dal controller,
 * più alcuni metodi aggiuntivi minori
 * 
 * @author Antonio Pirani
 * @author Matteo Fabbri
 *
 */
@Service
public class ServiceImplem implements it.univpm.weather.WeatherApp.service.Service { 
	//richiamando solo Service lo scambia per una interfaccia predefinita di Spring
	
	/**
	 * {@value} apiKey chiave privata per l'accesso alle API
	 */
	private final String apiKey = "e75a0a03e0d0542a15e263930e56f99a";

	/**
	 * Metodo che permette di ottenere le informazioni meteo relative alla temperatura reale e 
	 * percepita attuali della città specificata
	 * 
	 * @param cityName stringa contenente il nome della città da cercare
	 * @return obj oggetto JSON con tutte le informazioni ottenute con la One Call API corrente
	 * @throws CityNotFoundException eccezione per quando la città inserita non viene trovata
	 * @throws IOException eccezione di input/output
	 */
	public City getTemperature(String cityName) throws IOException, CityNotFoundException {

		JSONParser parser = new JSONParser();
		City city = new City();
		
		try {
			
			city = getCityCoords(cityName);
			
		} catch (IOException | CityNotFoundException e) {

			e.printStackTrace();
			throw new CityNotFoundException("Città " + cityName + " non trovata", cityName);
			
		}

		String url = "https://api.openweathermap.org/data/2.5/onecall?lat=" + city.getCoords().getLat() + "&lon=" + city.getCoords().getLon() + "&exclude=hourly,daily,minutely,alerts&appid=" + apiKey +"&units=metric";
		
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
	   
	    obj = (JSONObject) obj.get("current");
	    
	    city.setCurrentTemp(new Temperature ((long)obj.get("dt"), doubleValue(obj.get("feels_like")), doubleValue(obj.get("temp")) ));
	    
		return city;
	}
	
	/**
	 * Metodo che permette di ottenere le coordinate per una specifica città, necessarie per la One Call API
	 * Il metodo è suddiviso in due parti: nella prima si fa la chiamata alla geo API per prendere le coordinate 
	 * della città richiesta, mentre nella seconda si richiama la weather API per prelevare le informazioni che 
	 * nella prima chiamata non venivano restituite, ossia la timezone e l'id della città  
	 * 
	 * @param cityName stringa contenente il nome della città da cercare
	 * @return City istanza di City con latitudine, longitudine, timezone, id e nome della città richiesta
	 * @throws IOException per errori di input/output
	 * @throws CityNotFoundException eccezione per quando non la città desiderata non è stata trovata
	 */
	public City getCityCoords(String cityName) throws IOException, CityNotFoundException {
		
		JSONParser parser = new JSONParser();
		Coordinates coord = null;
		City city = new City();
		
		String url = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=1&appid=" + apiKey;
		
		InputStream input = new URL(url).openConnection().getInputStream();
		
		try {                
			
	      BufferedReader re = new BufferedReader(new InputStreamReader(input));  
	    
	      String text = read(re);  
	      String testo = (text.substring(0, text.length() - 1)); //per togliere ?
	      
	      if(testo.equals("[]")) {
	    	  throw new CityNotFoundException();
	      }
	      
	      testo = (testo.substring(1, testo.length() - 1)); //per togliere { e }
	      
	      JSONObject obj = (JSONObject) parser.parse(testo);
	      
	      double lat = (double) obj.get("lat");
	      double lon = (double) obj.get("lon");
	      
	      coord = new Coordinates(lat, lon);
	      
	      city.setCityName(cityName);
	      city.setCoords(coord);
	      
	      //return city;
	      
	    } catch (ParseException e) {
	    
	      System.out.println(e);
	      
	      return null;
	      
	    } finally {
	    	
	      input.close();
	      
	    }
		
		url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey + "&units=metric";
	    input = new URL(url).openConnection().getInputStream();
		
		try {                
			
	      BufferedReader re = new BufferedReader(new InputStreamReader(input));  
	    
	      String text = read(re);  
	      String testo = (text.substring(0, text.length() - 1)); //per togliere ?
	      
	      JSONObject obj = (JSONObject) parser.parse(testo);
	      
	      long id = (long) obj.get("id");
	      int timeZone = intValue(obj.get("timezone"));
	      
	      city.setCityId(id);
	      city.setTimeZone(timeZone);
	      
	      return city;
	      
	    } catch (ParseException e) {
	    
	      System.out.println(e);
	      
	      return null;
	      
	    } finally {
	    	
	      input.close();
	      
	    }
	}

	/**
	 * Metodo che permette di salvare su file la temperatura corrente. Il nuovo file viene salvato 
	 * nella cartella "files" con lo stesso nome della città ricercata. Il metodo non scrive sul file 
	 * se non è passata almeno 1 ora (cosa che si verifica se l'utente richiama più volte la rotta /current) 
	 * 
	 * @param City city che contiene tutte le informazioni principali da salvare su file
	 * @throws IOException in caso di problemi sul file
	 * @throws HourException se non è passata almeno 1 ora dall'ultimo salvataggio
	 * @return true se il salvataggio è riuscito correttamente
	 * @return false se il salvataggio NON è riuscito
	 */
	public boolean saveCurrentTemp(City city) throws IOException, HourException {
		
		String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "files" + System.getProperty("file.separator") + city.getCityName() + ".txt";
		//System.out.println("percorso: " + filePath); //C:\Users\anton\git\progettoOOP_Fabbri_Pirani\WeatherApp\files\
		
		File file = new File(filePath);
		//Writer writer = null; non funziona l'append con Writer
		
		if(checkLastLineDate(file, city.getCurrentTemp().getDateTime())) 
			throw new HourException();
			
		BufferedWriter bufferedWriter = null;
		
		try  {
			
			bufferedWriter = new BufferedWriter(new FileWriter(file, true));
			
			JSONObject obj = cityToJson(city);
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

	/**
	 * Metodo che prova a salvare ogni ora i dati correnti di una città su un file tramite 
	 * l'uso di uno scheduler. I vari file si possono trovare nella cartella "WeatherApp\files".
	 * 
	 * @param cityName nome della città da salvare
	 * @throws IOException per errori input/output
	 * 
	 */
	public void saveEveryHour(String cityName) throws IOException {
	
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			
		    @Override
		    public void run() {
		        
		    	try {
		    		
		    		City city = getTemperature(cityName);
		    		
					if(saveCurrentTemp(city)) 
						System.out.println("Salvataggio riuscito");
						
					else 
						System.out.println("Salvataggio non riuscito - riprovare più tardi");
						
				} catch (IOException | HourException e) {

					System.out.println("Differenza di orario inferiore ad 1 ora");
					System.out.println(e);
					//e.printStackTrace();
					
				} catch (CityNotFoundException e) {
					e.printStackTrace();
				}
		    }
		    
		}, 0, 1, TimeUnit.HOURS); //https://stackoverflow.com/questions/32228345/run-java-function-every-hour
		
	}

	/**
	 * Metodo che permette di confrontare le temperature attuali e di X giorni fa, tramite una media, 
	 * ritornando l'eventuale differenza di temperatura. Il numero di giorni indietro deve essere
	 * inferiore a 5, poichè questo è il limite stabilito dalla API gratuita
	 * 
	 * @param cityName nome della città di cui si vogliono confrontare le temperature
	 * @param prevDay periodo di tempo corrispondente al numero di giorni precedenti all'attuale
	 * @return JSONObject contenente le informazioni confrontate
	 * @throws CityNotFoundException eccezione per quando la città non viene trovata
	 */
	public JSONObject compareTemp(String cityName, int prevDay) throws IOException, ParseException, CityNotFoundException {

		City current = null;
		
		try {
			
			current = getTemperature(cityName); //corrente
			
		} catch(CityNotFoundException e) {
			throw new CityNotFoundException("Città " + cityName + " non trovata", cityName);
		}
	
		long dt = previousDay(prevDay);
		
		String giorno = prevDay == 1 ? " giorno" : " giorni";
		
		JSONArray previous = timeMachine(cityName, dt); //prevDay giorni precedenti al corrente
		Iterator<?> i = previous.iterator();
		
		ArrayList<Temperature> array = new ArrayList<Temperature>();
		
		double sumTemp = 0, sumFeels = 0;
		
		while(i.hasNext()) {
			
			JSONObject obj = (JSONObject) i.next(); 
			
			//necessita di doubleValue per correggere l'errore di casting tra long e double
			double temp = doubleValue(obj.get("temp"));
			double feels = doubleValue(obj.get("feels_like"));
			
			Temperature temperature = new Temperature(0, temp, feels);
			array.add(temperature);
			
			sumTemp += temp;
			sumFeels += feels;
			
		}
		
		current.setTemp(array);
		
		double avgTemp = sumTemp / previous.size();  
		double avgFeels = sumFeels / previous.size();

		//per settare la precisione ed evitare troppe cifre dopo la virgola
		Double compT = BigDecimal.valueOf(current.getCurrentTemp().getTemp() - avgTemp)
			    .setScale(3, RoundingMode.HALF_UP)
			    .doubleValue();
		
		Double compF = BigDecimal.valueOf(current.getCurrentTemp().getTemp() - avgFeels)
			    .setScale(3, RoundingMode.HALF_UP)
			    .doubleValue();
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("cityName", cityName);
		
		HashMap<String,Object> aux = new HashMap<String,Object>();
		
		if(compT > 0.0) {
			aux.put("result", "La temperatura reale rispetto alla media di " + prevDay + giorno + " fa è aumentata di " + compT + " gradi");
		}
		
		else {
			aux.put("result", "La temperatura reale rispetto alla media di " + prevDay + giorno + " fa è diminuita di " + Math.abs(compT) + " gradi");
		}
		
		map.put("temp", aux);
		aux =  new HashMap<String,Object>();
		
		if(compF > 0.0) {
			aux.put("result", "La temperatura percepita rispetto alla media di " + prevDay + giorno + " fa è aumentata di " +compF + " gradi");
		}
		
		else {
			aux.put("result", "La temperatura percepita rispetto alla media di " + prevDay + giorno + " fa è diminuita di " + Math.abs(compF) + " gradi");
		}

		map.put("feels_like", aux);
		JSONObject obj = new JSONObject(map);
		
		return obj;
		
	}
	
	/**
	 * Metodo che permette di ottenere le informazioni passate relative alla temperatura di una città. 
	 * La API considera il giorno a partire dalle 00:00 fino alle 23:59 dello stesso giorno, 
	 * piuttosto che dall'orario passato per parametro, e il giorno successivo
	 * 
	 * @param cityName nome della città
	 * @param dt data in formato UNIX relativa al giorno passato
	 * @return array JSONArray contenente tutte le informazioni
	 * @throws IOException eccezione per errori di input/output
	 */
	public JSONArray timeMachine(String cityName, long dt) throws IOException {

		JSONParser parser = new JSONParser();
		City city = new City();
		
		try {
			
			city = getCityCoords(cityName);
			
		} catch (CityNotFoundException e) {
			
			e.printStackTrace();
		}

		String url = "https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=" + city.getCoords().getLat() + "&lon=" + city.getCoords().getLon() + "&dt=" + dt + "&appid=" + apiKey + "&units=metric";
		
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
	    
	    //filtraggio
	    JSONArray array = (JSONArray) obj.get("hourly");
	    
		return array;
	}
	
	//Quelli che seguono sono metodi aggiuntivi utili al corretto funzionamento
	//del programma
	
	/**
	 * Metodo che legge tutto in contenuto dell'oggetto passato (lettura URL)
	 * 
	 * @param re Lettore contenente le informazioni
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
	    
	    return str.toString();

	}
	
	/**
	 * Metodo che calcola il valore in formato UNIX del giorno scelto rispetto alla data attuale
	 * 
	 * @param d numero di giorni indietro
	 * @return now data calcolata
	 */
	public long previousDay(int d) {
		
        long now = Instant.now().getEpochSecond();
        return now - d*3600*24;
        
    }
	
	/** Metodo per tradurre le informazioni principali della City in formato JSON
	 * 
	 * @param city istanza City da tradurre
	 * @return obj JSON da salvare
	 */
	public JSONObject cityToJson(City city) {

		JSONObject obj = new JSONObject();
		HashMap<String,Object> map = new HashMap<String,Object>();

	    map.put("name", city.getCityName());
		map.put("lat", city.getCoords().getLat());
		map.put("lon", city.getCoords().getLon());

		map.put("temp", city.getCurrentTemp().getTemp());
		map.put("feels_like", city.getCurrentTemp().getFeelsLike());
		map.put("dt", city.getCurrentTemp().getDateTime());

		obj = new JSONObject(map);

		return obj;

	}
	
	/**
	 * Metodo che controlla se la differenza tra il valore "dt" dell'ultima riga del file passato 
	 * e il valore del valore "dt" preso dall'url è superiore a 1 ora
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
	
	
	/**
	 * Metodo che ritorna un double. Utilizzato poichè a seconda del valore in un formato JSON, il dato 
	 * viene interpretato in modo differente. Per esempio Long e Double: 0 lo considera Long anche se il programma 
	 * necessita di un Double
	 * 
	 * @param value Oggetto su cui effettuare il casting in double
	 * @return double valore corretto
	 */
	private static double doubleValue(Object value) {
	    return (value instanceof Number ? ((Number)value).doubleValue() : -1.0);
	}
	
	/**
	 * Metodo analogo a doubleValue. Restituisce un oggetto intero
	 * 
	 * @param value Oggetto su cui effettuare il casting in int
	 * @return int valore corretto
	 */
	private static int intValue(Object value) {
	    return (int) (value instanceof Number ? ((Number)value).intValue() : -1.0);
	}

}
