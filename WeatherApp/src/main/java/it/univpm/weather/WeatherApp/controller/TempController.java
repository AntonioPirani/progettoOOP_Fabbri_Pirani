package it.univpm.weather.WeatherApp.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.HashMap;

import it.univpm.weather.WeatherApp.exceptions.CityNotFoundException;
import it.univpm.weather.WeatherApp.exceptions.HourException;
import it.univpm.weather.WeatherApp.service.Service;
import it.univpm.weather.WeatherApp.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

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
	@GetMapping(value="/current")
    public ResponseEntity<Object> getTemperature(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) throws HourException {
		
		try {
			
			JSONObject obj = service.getTemperature(cityName);
			
			if(obj.get("lat").toString().equals("0") && obj.get("lon").toString().equals("0")) {
				return new ResponseEntity<> ("Città non trovata", HttpStatus.NOT_FOUND);
			}
			
			//service.saveCurrentTemp(obj);
			service.saveEveryHour(cityName);
			
			return new ResponseEntity<> (obj.toString() + "\nSalvataggio delle informazioni ogni ora in corso", HttpStatus.OK);
			
		} catch (IOException e) {
			
			return new ResponseEntity<> ("Qualcosa è andato storto", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
    }
	
	@GetMapping("/compare")
	ResponseEntity<Object> compare(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName, @RequestParam(value = "previousDay", defaultValue = "1") int prevDay) {
		
		if(prevDay < 1 || prevDay > 5) {
			return new ResponseEntity<> ("Il numero di giorni precedenti da ricercare deve essere tra 1 e 5", HttpStatus.NOT_FOUND);
		}
		
	    return new ResponseEntity<>("confronto temperatura di un città in un dato range temporale", HttpStatus.OK);
	
	}
	
//	@GetMapping("/")
//	ResponseEntity<String> hello() {
//	    return new ResponseEntity<>("WeatherApp per la Temperatura - by Matteo Fabbri e Antonio Pirani", HttpStatus.OK);
//	}
	
	//non necessario - solo per prova
	@GetMapping(value="/coords")
    public ResponseEntity<Object> getCoordinates(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) {
		
		try {
			
			Coordinates coords = service.getCityCoords(cityName);
			
			if(coords.getLat() == 0.0 && coords.getLon() == 0.0) {
				return new ResponseEntity<> ("Città non trovata", HttpStatus.NOT_FOUND);
			}
			
			HashMap<String,Object> map = new HashMap<String,Object>();
			
			map.put("name", cityName);		
			
			map.put("lat", coords.getLat());
			map.put("lon", coords.getLon());

			JSONObject obj = new JSONObject(map);
			
			return new ResponseEntity<> (obj.toString(), HttpStatus.OK);
			
		} catch (IOException | CityNotFoundException e) {
			
			e.printStackTrace();
			return new ResponseEntity<> ("E' occorso un errore", HttpStatus.INTERNAL_SERVER_ERROR);

		}
    }
	
}
