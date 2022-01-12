package it.univpm.weather.WeatherApp.controller;

import org.json.simple.parser.ParseException;

import java.io.IOException;

import it.univpm.weather.WeatherApp.exceptions.*;
import it.univpm.weather.WeatherApp.filters.*;
import it.univpm.weather.WeatherApp.service.Service;
import it.univpm.weather.WeatherApp.service.ServiceImplem;
import it.univpm.weather.WeatherApp.model.*;
import it.univpm.weather.WeatherApp.stats.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/**Classe che gestisce le chiamate utente tramite rotte GET e POST
 * 
 * @author Antonio Pirani
 *
 */
@RestController
public class TempController {
	
	@Autowired
	Service service;
	//https://stackoverflow.com/questions/21282919/spring-3-request-processing-failed-nested-exception-is-java-lang-nullpointerexc/21329173
	
	/**Rotta di tipo GET per ottenere la temperatura corrente di una città
	 * 
	 * @param cityName città da cercare
	 * @return obj JSONObject con le informazioni richieste: nome, coordinate, data, temperatura attuale e percepita
	 * @throws HourException 
	 */
	@GetMapping(value = "/current")
    public ResponseEntity<Object> getTemperature(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) throws HourException {
		
		try {
			
			City city = service.getTemperature(cityName);
			
			if(city.getCoords().getLat() == 0 && city.getCoords().getLon() == 0) {
				return new ResponseEntity<> ("<br><center><h4>Città <b>\"" + cityName + "\"</b> non trovata</h4></center>", HttpStatus.NOT_FOUND);
			}
			
			//service.saveCurrentTemp(obj);
			service.saveEveryHour(cityName);
			
			return new ResponseEntity<> (city.toJson().toString() + "<br><br>Creazione dello storico in corso", HttpStatus.OK);
			
		} catch (IOException e) {
			
			return new ResponseEntity<> ("Qualcosa è andato storto", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
    }
	
	/** Rotta di tipo GET per confrontare lo storico sulle temperature effettive e percepite di una città.
	 * 
	 * @param cityName
	 * @param prevDay
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@GetMapping(value = "/compare")
	public ResponseEntity<Object> compare(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName, @RequestParam(value = "previousDay", defaultValue = "1") int prevDay) throws IOException, ParseException {
		
		if(prevDay < 1 || prevDay > 5) {
			return new ResponseEntity<> ("<br><center><h4>Il numero di giorni precedenti da ricercare deve essere tra <b>1</b> e <b>5</b></h4></center>", HttpStatus.NOT_FOUND);
		}
		
		String mex = service.compareTemp(cityName, prevDay);
		
		if(mex.equals("0")) {
			
			return new ResponseEntity<> ("<br><center><h4>Città \"<b>" + cityName + "\"</b> non trovata</h4></center>", HttpStatus.NOT_FOUND);
			
		}
		
	    return new ResponseEntity<>(mex, HttpStatus.OK);
	
	}
	
	/**  Rotta di tipo GET per restituire il filtraggio delle statistiche in base alla periodicità: giorni, fascia oraria, settimanale.
	 * 
	 * @param cityName
	 * @param filterBy
	 * @param time
	 * @return
	 */
	@GetMapping(value = "/statistics")
	public ResponseEntity<Object> statistics(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName,
										@RequestParam(value = "filterBy", required = false) String filterBy,
										@RequestParam(value = "time", required = false) Integer time) {
 
		StatsImplem stats = new StatsImplem();
		
		if(filterBy == null) { //tutto lo storico
			
			Statistics statsTemp = new Statistics();
			Statistics statsFeels = new Statistics();
			
			statsTemp = stats.getStats(cityName, true);
			statsFeels = stats.getStats(cityName, false);
			
			if(statsTemp == null ) {
				
				return new ResponseEntity<> ("<br><center><h4>Non è stato possibile calcolare le statistiche</h4></center>", HttpStatus.NOT_FOUND);
				
			}
			
		    return new ResponseEntity<>("Statistiche di <b>" + cityName + "</b><br><br>Temperatura reale:<br>" + statsTemp.toJson().toString() + "<br><br>" + "Temperatura percepita:<br>" + statsFeels.toJson().toString(), HttpStatus.OK);
		    
		}
		
		else {
			
			Filter filter;
			
//			if(time == 0) {
//				return new ResponseEntity<> ("<br><center><h4>Non è stato inserito un periodo di tempo valido</h4></center>", HttpStatus.BAD_REQUEST);
//			}
			
			try {
			
				switch(filterBy) {
				
					case "hour": 
						
						filter = new FilterByHour(cityName, time);
						return new ResponseEntity<> (( (FilterByHour) filter).calculate(), HttpStatus.OK);
					
					case "day": 
						
						filter = new FilterByDay(cityName, time);
						return new ResponseEntity<> (( (FilterByDay) filter).calculate(), HttpStatus.OK);
						
					case "week": 
						
						filter = new FilterByWeek(cityName, time);
						return new ResponseEntity<> (( (FilterByWeek) filter).calculate(), HttpStatus.OK);
	
					default: 
						
						return new ResponseEntity<> ("<br><center><h4>Il filtro inserito non è corretto</h4><br>Le opzioni sono hour, day, week</center>", HttpStatus.BAD_REQUEST);
				
				}
			
			} catch(InvalidPeriodException | HistoryException e) {
				return new ResponseEntity<> (e.getMessage(), HttpStatus.BAD_REQUEST);
				
			}
			
		}
		
	}
	
//	@GetMapping("/")
//	ResponseEntity<String> hello() {
//	    return new ResponseEntity<>("WeatherApp per la Temperatura - by Matteo Fabbri e Antonio Pirani", HttpStatus.OK);
//	}
	
	//non necessario - solo per prova
//	@GetMapping(value="/coords")
//    public ResponseEntity<Object> getCoordinates(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) {
//		
//		try {
//			
//			Coordinates coords = service.getCityCoords(cityName);
//			
//			if(coords.getLat() == 0.0 && coords.getLon() == 0.0) {
//				return new ResponseEntity<> ("Città non trovata", HttpStatus.NOT_FOUND);
//			}
//			
//			HashMap<String,Object> map = new HashMap<String,Object>();
//			
//			map.put("name", cityName);		
//			
//			map.put("lat", coords.getLat());
//			map.put("lon", coords.getLon());
//
//			JSONObject obj = new JSONObject(map);
//			
//			return new ResponseEntity<> (obj.toString(), HttpStatus.OK);
//			
//		} catch (IOException | CityNotFoundException e) {
//			
//			e.printStackTrace();
//			return new ResponseEntity<> ("E' occorso un errore", HttpStatus.INTERNAL_SERVER_ERROR);
//
//		}
//    }
	
}
