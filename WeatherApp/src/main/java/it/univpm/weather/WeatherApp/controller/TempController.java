package it.univpm.weather.WeatherApp.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

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
	
	/**
	 * Rotta di tipo GET per ottenere le temperatura reali e percepite attuali di una città. 
	 * Inoltre si occupa di salvare le informazioni con cadenza oraria su specifici file, 
	 * creando così gli storici necessari alla eventuale analisi delle statistiche.
	 * 
	 * @param cityName città da cercare, con valore di default = "Ancona"
	 * @return JSONObject con le informazioni richieste: nome, coordinate, data, temperatura attuale e percepita
	 * @throws HourException eccezione che viene lanciata se la differenza di orario tra la data attuale e quella presente nell'ultima riga del 
	 * 		file è inferiore a 1 ora
	 * @throws CityNotFoundException eccezione che indica che la città immessa non è stata trovata
	 */
	@GetMapping(value = "/current")
    public ResponseEntity<Object> getTemperature(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) throws HourException, CityNotFoundException {
		
		try {
			
			City city = service.getTemperature(cityName);
		
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
	 * @return JSONObject contenente le informazioni sul confronto effettuato
	 * @throws IOException eccezione per errori di input/output
	 * @throws ParseException eccezione per errori di parsing del JSON
	 * @throws CityNotFoundException eccezione che indica che la città immessa non è stata trovata
	 */
	@GetMapping(value = "/compare")
	public ResponseEntity<Object> compare(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName, @RequestParam(value = "previousDay", defaultValue = "1") int prevDay) throws IOException, ParseException, CityNotFoundException {
		
		if(prevDay < 1 || prevDay > 5) {
			return new ResponseEntity<> (genericException("InputException", "Il numero di giorni precedenti da ricercare deve essere tra 1 e 5"), HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			return new ResponseEntity<>(service.compareTemp(cityName, prevDay), HttpStatus.OK); 
			
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
	 * @return JSONObject con le informazioni delle statistiche calcolate
	 * @throws ParseException eccezione per errori di parsing nello storico
	 * @throws HistoryException eccezione dello storico (mancanza del file oppure dati non sufficienti al
	 * 		calcolo delle statistiche)
	 * @throws InvalidPeriodException eccezione per quando si inserisce un periodo di tempo non valido ( < 1 )
	 */
	@GetMapping(value = "/statistics")
	public ResponseEntity<Object> statistics(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName,
										@RequestParam(value = "filterBy", required = false) String filterBy,
										@RequestParam(value = "time", required = false) Integer time) throws ParseException, InvalidPeriodException, HistoryException {
 
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
						
						return new ResponseEntity<> (genericException("FilterException", "Il filtro inserito non è corretto. Le opzioni sono hour, day, week"), HttpStatus.BAD_REQUEST);
				
				}
			
			} catch(InvalidPeriodException e) {
				return new ResponseEntity<> (e.toJson(), HttpStatus.BAD_REQUEST);
			} catch(HistoryException e) {
				return new ResponseEntity<> (e.toJson(), HttpStatus.NOT_FOUND);
			} catch(NullPointerException e) {
				return new ResponseEntity<> (genericException("NullPointerException", "Non è stato inserito un periodo di tempo"), HttpStatus.NOT_FOUND);
			}
			
		}
		
	}
	
	/**
	 * Metodo per creare un JSONObject contentente le eccezioni non gestite tramite apposite classi
	 * 
	 * @param exception Nome della eccezione
	 * @param mex Messaggio da stampare
	 * @return JSONObject con le informazioni
	 */
	public JSONObject genericException(String exception, String mex) {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("exception", exception);
		map.put("mex", mex);
		
		JSONObject obj = new JSONObject(map);
		
		return obj;
	}
	
}
