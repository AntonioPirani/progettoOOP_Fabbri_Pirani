package it.univpm.weather.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import org.json.simple.JSONArray;

import it.univpm.weather.exceptions.CityNotFoundException;
import it.univpm.weather.model.*;
import it.univpm.weather.service.Service;

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
    public ResponseEntity<Object> getVisibility(@RequestParam(value = "cityName", defaultValue = "Ancona") String cityName) {
		
		try {
			
			return new ResponseEntity<Object> (service.getTemperature(cityName).toString(), HttpStatus.OK);
			
		} catch (Exception e) {
			
			return new ResponseEntity<Object> (HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
    }
	
	@GetMapping("/hello")
	ResponseEntity<String> hello() {
	    return new ResponseEntity<>("Hello World!", HttpStatus.OK);
	}
	
	
	
	

}
