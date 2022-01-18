package it.univpm.weather.WeatherApp.errors;

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
	public String getErrorPath() {
		String content="<html>\r\n"
				+ "	<style>\r\n"
				+ "		table, th, td {\r\n"
				+ "		border:1px solid black;\r\n"
				+ "		border-collapse: collapse;\r\n"
				+ "		border-radius: 83px;\r\n"
				+ "		text-align:center;\r\n"
				+ "		margin: 15px auto auto auto;\r\n"
				+ "		color:#292929;\r\n"
				+ "		}\r\n"
				+ "		div{\r\n"
				+ "		text-align:justify;\r\n"
				+ "		margin: 7px 7px 7px 7px;\r\n"
				+ "		}\r\n"
				+ "	</style>\r\n"
				+ "	<head>\r\n"
				+ "		<title>Error Page</title>\r\n"
				+ "		<link href=\"icon.png\">\r\n"
				+ "	</head>\r\n"
				+ "	<body style=\"background: -webkit-gradient(linear, left top, left bottom, from(#d9f9ff ), to(#fff)) fixed;\">\r\n"
				+ "		<center><div style=\"border:2px solid grey; width:53%;  border-radius: 50px; margin-top:8px; background-color:#fffb96 ;\">\r\n"
				+ "		<h2 style=\"text-align:center; color: #DE6806 ;\">PROGETTO PROGRAMMAZIONE OGGETTI</h2>\r\n"
				+ "		<h3 style=\"text-align:center;color:#DE6806 ;margin-top:1px;\"><i>based on OpenWeather</i></h3></div></center>\r\n"
				+ "		<table style=\"width:95%\">\r\n"
				+ "			<tr>\r\n"
				+ "				<th width = 7% style=\"font-size:25px\">Nome</th>\r\n"
				+ "				<th width = 55% style=\"font-size:25px\">Esempio</th>\r\n"
				+ "				<th width = 50% style=\"font-size:25px\">Descrizione</th>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td style=\"font-size:20px\">/</td>\r\n"
				+ "				<td style=\"font-size:20px\">http://localhost:8080/</td>\r\n"
				+ "				<td style=\"font-size:20px\"><div>Rotta di default/errore.</div></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td style=\"font-size:20px\">/current</td>\r\n"
				+ "				<td style=\"font-size:20px\">http://localhost:8080/current?cityName=Ancona</td>\r\n"
				+ "				<td style=\"font-size:20px\"><div>Rotta di tipo GET per ottenere la temperatura corrente di una città, con la possibilità di salvare i dati con cadenza oraria.</div></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td style=\"font-size:20px\">/compare</td>\r\n"
				+ "				<td style=\"font-size:20px\">http://localhost:8080/compare?cityName=Ancona&previousDay=1</td>\r\n"
				+ "				<td style=\"font-size:20px\"><div>Rotta di tipo GET per confrontare le temperature correnti e percepite, in dato range tamporale in giorni.</div></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td style=\"font-size:20px\">/statistics</td>\r\n"
				+ "				\r\n"
				+ "				<td style=\"font-size:20px\">\r\n"
				+ "					<ol style=\"text-align:justify;\">\r\n"
				+ "						<li> http://localhost:8080/statistics?cityName=Ancona</li>\r\n"
				+ "						<li> http://localhost:8080/statistics?cityName=Ancona&filterBy=\"hour\"&time=1</li>\r\n"
				+ "						<li>http://localhost:8080/statistics?cityName=Ancona&filterBy=\"day\"&time=1</li>\r\n"
				+ "						<li>http://localhost:8080/statistics?cityName=Ancona&filterBy=\"week\"&time=1</li>\r\n"
				+ "					</ul>\r\n"
				+ "				</ol>\r\n"
				+ "				</td>\r\n"
				+ "				<td style=\"font-size:20px\"> <div>Rotta di tipo GET per restituire il filtraggio delle statistiche in base alla periodicità: fascia oraria, giornaliera e settimanale. Inoltre, se il filtro non viene specificato, viene analizzato tutto lo storico.</div></td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "		<center><img src=\"icon.png\" style=\"width:88px;height:88px; margin-top:20px;\"></center>\r\n"
				+ "		<footer style=\"margin-top:0px\">\r\n"
				+ "			<p style=\"text-align:center;\"><i>Created by Antonio Pirani & Matteo Fabbri. © 2021/22</i></p>\r\n"
				+ "		</footer>\r\n"
				+ "	</body>\r\n"
				+ "</html>";
		return content;
	}
}
