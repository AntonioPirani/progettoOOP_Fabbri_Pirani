package it.univpm.weather.WeatherApp.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

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

/**
 * Controller del progetto, gestisce le chiamate utente sotto forma di rotte GET, e adibisce
 * al funzionamento dell'intero programma 
 * 
 * @author Antonio Pirani
 * @author Matteo Fabbri
 *
 */
@RestController
public class TempController {
	
	@Autowired
	Service service;
	//https://stackoverflow.com/questions/21282919/spring-3-request-processing-failed-nested-exception-is-java-lang-nullpointerexc/21329173
	
	/**
	 * Rotta di tipo GET per ottenere le temperatura reali e percepite attuali di una città. 
	 * Inoltre si occupa di salvare le informazioni con cadenza oraria su specifici file, 
	 * creando così gli storici necessari alla analisi delle statistiche.
	 * 
	 * @param cityName città da cercare, con valore di default = "Ancona"
	 * @return JSONObject con le informazioni richieste: nome, coordinate, data, temperatura attuale e percepita
	 * @throws HourException eccezione custom: se la differenza di orario tra la data attuale e quella presente nell'ultima riga del 
	 * 		file è inferiore a 1 ora
	 * @throws CityNotFoundException eccezione custom: se la città immessa non è stata trovata
	 */
	@GetMapping(value = "/current")
    public ResponseEntity<Object> getTemperature(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) throws HourException, CityNotFoundException {
		
		try {
			
			City city = service.getTemperature(cityName);
			
//			if(city.getCoords().getLat() == 0 && city.getCoords().getLon() == 0) {
//				return new ResponseEntity<> ("<br><center><h4>Città <b>\"" + cityName + "\"</b> non trovata</h4></center>", HttpStatus.NOT_FOUND);
//			}
			
			//service.saveCurrentTemp(obj);
			service.saveEveryHour(cityName);
			
			return new ResponseEntity<> (city.toJson(), HttpStatus.OK);
			
		} catch (IOException e) {
			
			return new ResponseEntity<> (genericException("IOException", "Errore nell'inserimento"), HttpStatus.NOT_FOUND);
		
		} catch(CityNotFoundException e) {
			
			return new ResponseEntity<> (e.toJson(), HttpStatus.NOT_FOUND);
			
		}
    }
	
	/**
	 * Rotta di tipo GET che restituisce la variazione delle temperature reali e percepite del giorno attuale 
	 * rispetto al numero di giorni inseriti nella richiesta (di default 1)
	 * 
	 * @param cityName Stringa con il nome della città di cui si vogliono confrontare le temperature dei giorni passati
	 * 		valore di default = "Ancona"
	 * @param prevDay numero di giorni precedenti a quello attuale (validi solo gli interi compresi tra 1 e 5)
	 * 		valore di default = 1
	 * @return mex Stringa mista contenente un testo in formato HTML alternato a oggetti JSON contenenti le informazioni delle temperature
	 * @throws IOException eccezione per errori di input/output
	 * @throws ParseException eccezione per errori di parsing del JSON
	 */
	@GetMapping(value = "/compare")
	public ResponseEntity<Object> compare(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName, @RequestParam(value = "previousDay", defaultValue = "1") int prevDay) throws IOException, ParseException {
		
		if(prevDay < 1 || prevDay > 5) {
			return new ResponseEntity<> (genericException("InputException", "Il numero di giorni precedenti da ricercare deve essere tra 1 e 5"), HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			return new ResponseEntity<>(service.compareTemp(cityName, prevDay), HttpStatus.OK); //TODO
			
		} catch (CityNotFoundException e) {
			
			return new ResponseEntity<> (e.toJson(), HttpStatus.NOT_FOUND);
			
		}
		
	}
	
	/**
	 * Rotta di tipo GET che permette di visualizzare le statistiche della città inserita, 
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
	 * @throws FileNotFoundException, HistoryException eccezioni in caso di mancanza dello storico
	 * @throws ParseException eccezione per errori di parsing nello storico
	 * @throws InvalidPeriodException eccezione per quando si inserisce un periodo di tempo non valido ( < 1 )
	 */
	@GetMapping(value = "/statistics")
	public ResponseEntity<Object> statistics(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName,
										@RequestParam(value = "filterBy", required = false) String filterBy,
										@RequestParam(value = "time", required = false) Integer time) throws FileNotFoundException, ParseException, InvalidPeriodException, HistoryException {
 
		
		
		if(filterBy == null) { //tutto lo storico
			
			try {
				
				StatsImplem stats = new StatsImplem();
				return new ResponseEntity<>(stats.calculate(cityName), HttpStatus.OK);
				
			} catch (HistoryException e) {
				
				return new ResponseEntity<> (e.toJson(), HttpStatus.NOT_FOUND);
				
			} catch (ParseException e) {
				
				return new ResponseEntity<> (genericException("ParseException", "Errore nel parsing dello storico di " + cityName), HttpStatus.NOT_FOUND);
				
			}
			
		}
		
		else {
			
			Filter filter;
			
			try {
			
				switch(filterBy) { //TODO
				
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
						
						return new ResponseEntity<> (genericException("FilterException", "Il filtro inserito non è corretto. Le opzioni sono hour, day, week"), HttpStatus.BAD_REQUEST);
				
				}
			
			} catch(InvalidPeriodException e) {
				return new ResponseEntity<> (e.toJson(), HttpStatus.BAD_REQUEST);
			} catch(HistoryException e) {
				return new ResponseEntity<> (e.toJson(), HttpStatus.NOT_FOUND);
			}
			
		}
		
	}
	
	public JSONObject genericException(String exception, String mex) {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("exception", exception);
		map.put("mex", mex);
		
		JSONObject obj = new JSONObject(map);
		
		return obj;
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
