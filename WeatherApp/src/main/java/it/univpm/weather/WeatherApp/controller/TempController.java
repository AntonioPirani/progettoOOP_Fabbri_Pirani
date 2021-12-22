package it.univpm.weather.WeatherApp.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.HashMap;

import it.univpm.weather.WeatherApp.exceptions.CityNotFoundException;
import it.univpm.weather.WeatherApp.service.Service;
import it.univpm.weather.WeatherApp.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class TempController {
	
	@Autowired
	Service service;
	//https://stackoverflow.com/questions/21282919/spring-3-request-processing-failed-nested-exception-is-java-lang-nullpointerexc/21329173
	
	@GetMapping(value="/current")
    public ResponseEntity<Object> getTemperature(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) {
		
		try {
			
			HashMap<String,Object> map = new HashMap<String,Object>();
			
			JSONObject obj = service.getTemperature(cityName);
			
			//TODO fare una classe apposita per generare il JSON
			map.put("name", cityName);
			map.put("lat", obj.get("lat"));
			map.put("lon", obj.get("lon"));
			
			obj = (JSONObject) obj.get("current");
			
			map.put("temp", obj.get("temp"));
			map.put("feels_like", obj.get("feels_like"));
			map.put("dt", obj.get("dt"));
			
			obj = new JSONObject(map);
			
			return new ResponseEntity<> (obj.toString(), HttpStatus.OK);
			
		} catch (IOException e) {
			
			return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
    }
	
	@GetMapping("/")
	ResponseEntity<String> hello() {
	    return new ResponseEntity<>("WeatherApp - Temperatura - by Matteo Fabbri e Antonio Pirani", HttpStatus.OK);
	}
	
	
	@GetMapping(value="/coords")
    public ResponseEntity<Object> getCoordinates(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) {
		
		try {
			
			Coordinates coords = service.getCityCoords(cityName);
			
			HashMap<String,Object> map = new HashMap<String,Object>();
			
			map.put("name", cityName);
			map.put("lat", coords.getLat());
			map.put("lon", coords.getLon());

			JSONObject obj = new JSONObject(map);
			
			return new ResponseEntity<> (obj.toString(), HttpStatus.OK);
			
		} catch (IOException e) {
			
			return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (CityNotFoundException e) {
			
			e.printStackTrace();
			return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);

		}
    }
	
	
	

}
