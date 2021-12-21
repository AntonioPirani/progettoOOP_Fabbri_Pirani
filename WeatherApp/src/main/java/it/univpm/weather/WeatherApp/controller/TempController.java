package it.univpm.weather.WeatherApp.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import java.io.IOException;

import it.univpm.weather.WeatherApp.exceptions.CityNotFoundException;
import it.univpm.weather.WeatherApp.service.Service;
import it.univpm.weather.WeatherApp.model.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class TempController {
	
	Service service;
	
	@GetMapping(value="/current")
    public ResponseEntity<Object> getTemperature(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) {
		
		try {
			
			return new ResponseEntity<> (service.getTemperature(cityName), HttpStatus.OK);
			
		} catch (Exception e) {
			
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
			
			return new ResponseEntity<> (service.getCityCoords(cityName), HttpStatus.OK);
			
		} catch (Exception e) {
			
			return new ResponseEntity<> (HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
    }
	
	
	

}
