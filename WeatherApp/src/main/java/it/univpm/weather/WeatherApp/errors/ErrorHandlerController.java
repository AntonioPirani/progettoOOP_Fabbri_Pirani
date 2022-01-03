package it.univpm.weather.WeatherApp.errors;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Matteo Fabbri
 *
 */
@RestController
public class ErrorHandlerController implements ErrorController{

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
				+ "		margin: 40px auto 20px auto;\r\n"
				+ "		color:#292929;\r\n"
				+ "		}\r\n"
				+ "	</style>\r\n"
				+ "	<head>\r\n"
				+ "		<title>Error Page</title>\r\n"
				+ "		<link href=\"icon.png\">\r\n"
				+ "	</head>\r\n"
				+ "	<body style=\"background: -webkit-gradient(linear, left top, left bottom, from(#d9f9ff ), to(#fff)) fixed;\">\r\n"
				+ "		<center><div style=\"border:2px solid grey; width:53%;  border-radius: 10px; margin-top:20px;\">\r\n"
				+ "		<h1 style=\"text-align:center; color:black;\">PROGETTO PROGRAMMAZIONE OGGETTI</h1>\r\n"
				+ "		<h3 style=\"text-align:center;color:#292929;margin-top:1px;\"><i>based on OpenWeather</i></h3></div></center>\r\n"
				+ "		<table style=\"width:80%\">\r\n"
				+ "			<tr>\r\n"
				+ "				<th width = 10% style=\"font-size:25px\">Nome</th>\r\n"
				+ "				<th width = 50% style=\"font-size:25px\">Esempio</th>\r\n"
				+ "				<th width = 45% style=\"font-size:25px\">Descrizione</th>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td style=\"font-size:20px\">/</td>\r\n"
				+ "				<td style=\"font-size:20px\">http://localhost:8080/</td>\r\n"
				+ "				<td style=\"font-size:20px\">Rotta di default/errore</td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td style=\"font-size:20px\">/current</td>\r\n"
				+ "				<td style=\"font-size:20px\">http://localhost:8080/current<br>http://localhost:8080/current?cityName=Ancona</td>\r\n"
				+ "				<td style=\"font-size:20px\">Rotta di tipo GET per ottenere la temperatura corrente di una città, con il metodo saveEveryHour che ci permette di salvarle con cadenta oraria</td>\r\n"
				+ "			</tr>\r\n"
				+ "				<tr>\r\n"
				+ "				<td style=\"font-size:20px\">/compare</td>\r\n"
				+ "				<td style=\"font-size:20px\">http://localhost:8080/compare<br>http://localhost:8080/compare?cityName=Ancona&prevDay=1</td>\r\n"
				+ "				<td style=\"font-size:20px\">Rotta di tipo GET per confrontare le temperature correnti e percepite, in dato range tamporale</td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "		<center><img src=\"icon.png\" style=\"width:88px;height:88px; margin-top:50px;\"></center>\r\n"
				+ "		<footer style=\"margin-bottom:0px\">\r\n"
				+ "			<p style=\"text-align:center;\"><i>Created by Antonio Pirani & Matteo Fabbri. © 2021</i></p>\r\n"
				+ "		</footer>\r\n"
				+ "	</body>\r\n"
				+ "</html>";
		return content;
	}
}
