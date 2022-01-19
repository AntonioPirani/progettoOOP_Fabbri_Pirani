package it.univpm.weather.WeatherApp.errors;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** Classe che personalizza il messaggio di errore della rotta /error.
 * 
 * @author Matteo Fabbri
 */
@RestController
public class ErrorHandlerController implements ErrorController{
	
	/** Metodo che restituisce il parametro content.
	 * 
	 * @return content Stringa contenente il corpo HTML della pagina personalizzata di errore.
	 */
	@RequestMapping("/error")
	@ResponseBody
	public JSONObject getErrorPath() {
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("error","Il comando inserito non è stato riconosciuto");
		map.put("errorPage","https://htmlpreview.github.io/?https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani/blob/main/WeatherApp/src/main/resources/static/errorPage.html");
		map.put("readMe", "https://github.com/AntonioPirani/progettoOOP_Fabbri_Pirani");
		
		JSONObject output = new JSONObject(map);
		
		return output;
	}
}
