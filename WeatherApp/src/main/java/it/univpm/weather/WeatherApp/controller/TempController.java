package it.univpm.weather.WeatherApp.controller;

import org.json.simple.parser.ParseException;

import java.io.IOException;

import it.univpm.weather.WeatherApp.exceptions.*;
import it.univpm.weather.WeatherApp.filters.*;
import it.univpm.weather.WeatherApp.service.Service;
import it.univpm.weather.WeatherApp.model.*;
import it.univpm.weather.WeatherApp.stats.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/**Classe che gestisce le chiamate utente tramite rotte GET 
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
	 * @param cityName città da cercare, con valore di default = "Ancona"
	 * @return JSONObject con le informazioni richieste: nome, coordinate, data, temperatura attuale e percepita
	 * @throws HourException se la differenza di orario tra la data attuale e quella presente nell'ultima riga del file è inferiore a 1 ora
	 */
	@GetMapping(value = "/current")
    public ResponseEntity<Object> getTemperature(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) throws HourException {
		
		try {
			
			City city = service.getTemperature(cityName);
			
//			if(city.getCoords().getLat() == 0 && city.getCoords().getLon() == 0) {
//				return new ResponseEntity<> ("<br><center><h4>Città <b>\"" + cityName + "\"</b> non trovata</h4></center>", HttpStatus.NOT_FOUND);
//			}
			
			//service.saveCurrentTemp(obj);
			service.saveEveryHour(cityName);
			
			return new ResponseEntity<> (city.toJson().toString() + "<br><br>Creazione dello storico in corso", HttpStatus.OK);
			
		} catch (IOException | CityNotFoundException e) {
			
			return new ResponseEntity<> ("<br><center><h4>Città <b>\"" + cityName + "\"</b> non trovata</h4></center>", HttpStatus.NOT_FOUND);
		
		}
    }
	
	/**Rotta di tipo GET che restituisce la variazione delle temperature reali e percepite del giorno attuale 
	 * rispetto al numero di giorni inseriti nella richiesta (di default 1)
	 * 
	 * @param cityName Stringa con il nome della città di cui si vogliono confrontare le temperature dei giorni passati
	 * @param prevDay numero di giorni precedenti a quello attuale (validi solo gli interi compresi tra 1 e 5)
	 * @return mex Stringa mista contenente un testo in formato HTML alternato a oggetti JSON contenenti le informazioni delle temperature
	 * @throws IOException
	 * @throws ParseException
	 */
	@GetMapping(value = "/compare")
	public ResponseEntity<Object> compare(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName, @RequestParam(value = "previousDay", defaultValue = "1") int prevDay) throws IOException, ParseException {
		
		if(prevDay < 1 || prevDay > 5) {
			return new ResponseEntity<> ("<br><center><h4>Il numero di giorni precedenti da ricercare deve essere tra <b>1</b> e <b>5</b></h4></center>", HttpStatus.NOT_FOUND);
		}
		
		String mex = "";
		
		try {
			
			mex = service.compareTemp(cityName, prevDay);
			
		} catch (CityNotFoundException e) {
			
			return new ResponseEntity<> ("<br><center><h4>Città \"<b>" + cityName + "\"</b> non trovata</h4></center>", HttpStatus.NOT_FOUND);
			
		}
		
	    return new ResponseEntity<>(mex, HttpStatus.OK);
	
	}
	
	/**Rotta di tipo GET che permette di visualizzare le statistiche della città inserita, 
	 * sia della temperature reale che quella percepita.
	 * Le informazioni vengono prelevate dai relativi storici creati con la rotta "/current" 
	 * (se esistenti) e, a seconda della modalità desiderata, si confronta tutto lo storico
	 * oppure tramite filtri si decide di analizzare solo una determinata cadenza temporale, 
	 * che può essere oraria, giornaliera o settimanale. Se non viene inserito nessun filtro
	 * si considera l'analisi di tutto lo storico esistente
	 * 
	 * @param cityName Nome della città di cui si vogliono conoscere le statistiche
	 * @param filterBy Stringa contentente il tipo di filtro che si vuole applicare
	 * @param time Quantità di tempo precendente all'orario attuale 
	 * @return Stringa mista a testo in HTML e JSON con tutte le informazioni richieste
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
